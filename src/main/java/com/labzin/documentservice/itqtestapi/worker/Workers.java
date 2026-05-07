package com.labzin.documentservice.itqtestapi.worker;

import com.labzin.documentservice.itqtestapi.persistance.entity.Document;
import com.labzin.documentservice.itqtestapi.persistance.enums.DocumentStatus;
import com.labzin.documentservice.itqtestapi.persistance.repository.DocumentRepository;
import com.labzin.documentservice.itqtestapi.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class Workers {

    private final DocumentRepository documentRepository;
    private final DocumentService documentService;

    @Value("${app.schedule.batch-size}")
    private int batchSize;
    int pageSub=0;
    int pageApp=0;

    @Scheduled(cron = "${app.schedule.time}")
    public void submitWorker() {

        while (true) {
            Pageable pageable = PageRequest.of(pageSub, batchSize);
            Page<Document> documentPage = documentRepository.findByStatus(
                    DocumentStatus.DRAFT,
                    pageable
            );

            List<Document> documents = documentPage.getContent();
            if (documents.isEmpty()) {
                break;
            }

            List<UUID> uuids = documents.stream()
                    .map(Document::getId)
                    .toList();

            documentService.agreement(uuids);
            if (documents.size() < batchSize) {
                break;
            }
            pageSub++;
        }
    }

    @Scheduled(cron = "${app.schedule.time}")
    public void approveWorker() {

        while (true) {
            Pageable pageable = PageRequest.of(pageApp, batchSize);
            Page<Document> documentPage = documentRepository.findByStatus(
                    DocumentStatus.SUBMITTED,
                    pageable
            );

            List<Document> documents = documentPage.getContent();
            if (documents.isEmpty()) {
                break;
            }

            List<UUID> uuids = documents.stream()
                    .map(Document::getId)
                    .toList();

            documentService.agreement(uuids);
            if (documents.size() < batchSize) {
                break;
            }
            pageApp++;
        }
    }
}
