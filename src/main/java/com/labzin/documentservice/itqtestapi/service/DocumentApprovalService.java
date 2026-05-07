package com.labzin.documentservice.itqtestapi.service;

import com.labzin.documentservice.itqtestapi.persistance.entity.ApprovalRegistry;
import com.labzin.documentservice.itqtestapi.persistance.entity.Document;
import com.labzin.documentservice.itqtestapi.persistance.entity.DocumentHistory;
import com.labzin.documentservice.itqtestapi.persistance.enums.Action;
import com.labzin.documentservice.itqtestapi.persistance.enums.DocumentStatus;
import com.labzin.documentservice.itqtestapi.persistance.enums.StatusChangeResult;
import com.labzin.documentservice.itqtestapi.persistance.repository.ApprovalRegistryRepository;
import com.labzin.documentservice.itqtestapi.persistance.repository.DocumentHistoryRepository;
import com.labzin.documentservice.itqtestapi.persistance.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentApprovalService {

    private final DocumentRepository documentRepository;
    private final DocumentHistoryRepository documentHistoryRepository;
    private final ApprovalRegistryRepository approvalRegistryRepository;


    public StatusChangeResult processDocumentApproval(UUID id) {
        Document document = documentRepository.findById(id).orElse(null);

        if (document == null) {
            return StatusChangeResult.NOT_FOUND;
        }

        if (document.getStatus() != DocumentStatus.SUBMITTED) {
            return StatusChangeResult.CONFLICT;
        }

        document.setStatus(DocumentStatus.APPROVED);
        documentRepository.save(document);

        DocumentHistory history = DocumentHistory.builder()
                .document(document)
                .action(Action.APPROVE)
                .actionBy(document.getAuthor())
                .comment("Документ утвержден")
                .build();
        documentHistoryRepository.save(history);

        ApprovalRegistry approvalRegistry = ApprovalRegistry.builder()
                .document(document)
                .build();

        approvalRegistryRepository.save(approvalRegistry);

        return StatusChangeResult.SUCCESS;
    }
}
