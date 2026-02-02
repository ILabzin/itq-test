package com.labzin.document_service_itq_test_api.service;

import com.labzin.document_service_itq_test_api.persistance.entity.ApprovalRegistry;
import com.labzin.document_service_itq_test_api.persistance.entity.Document;
import com.labzin.document_service_itq_test_api.persistance.entity.DocumentHistory;
import com.labzin.document_service_itq_test_api.persistance.enums.Action;
import com.labzin.document_service_itq_test_api.persistance.enums.DocumentStatus;
import com.labzin.document_service_itq_test_api.persistance.enums.StatusChangeResult;
import com.labzin.document_service_itq_test_api.persistance.repository.ApprovalRegistryRepository;
import com.labzin.document_service_itq_test_api.persistance.repository.DocumentHistoryRepository;
import com.labzin.document_service_itq_test_api.persistance.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
