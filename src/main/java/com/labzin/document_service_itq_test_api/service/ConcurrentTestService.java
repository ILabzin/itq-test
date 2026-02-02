package com.labzin.document_service_itq_test_api.service;

import com.labzin.document_service_itq_test_api.dto.ConcurrentTestResponse;
import com.labzin.document_service_itq_test_api.exception.NotFoundException;
import com.labzin.document_service_itq_test_api.persistance.entity.Document;
import com.labzin.document_service_itq_test_api.persistance.enums.Action;
import com.labzin.document_service_itq_test_api.persistance.repository.DocumentRepository;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConcurrentTestService {

    private final DocumentRepository documentRepository;
    private final DocumentApprovalService documentApprovalService;

    public ConcurrentTestResponse runConcurrentTest(UUID documentId, int threads, int attempts) {

        log.info("Starting concurrent test for document {}, threads: {}, attempts: {}",
                documentId, threads, attempts);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger conflictCount = new AtomicInteger(0);
        AtomicInteger errorCount = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(threads);

        for (int i = 0; i < threads * attempts; i++) {
            executor.submit(() -> {
                try {

                    documentApprovalService.processDocumentApproval(documentId);
                    successCount.incrementAndGet();
                } catch (OptimisticLockException | ObjectOptimisticLockingFailureException e) {
                    conflictCount.incrementAndGet();
                } catch (Exception e) {
                    log.error("Error during approval attempt", e);
                    errorCount.incrementAndGet();
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Test interrupted", e);
        }

        Document finalDocument = documentRepository.findById(documentId)
                .orElseThrow(() -> new NotFoundException("Document not found after test"));

        int historyCreated = finalDocument.getHistory().stream()
                .filter(h -> h.getAction() == Action.APPROVE)
                .mapToInt(h -> 1)
                .sum();

        return new ConcurrentTestResponse(
                threads * attempts,
                successCount.get(),
                conflictCount.get(),
                finalDocument.getStatus(),
                historyCreated
        );
    }
}
