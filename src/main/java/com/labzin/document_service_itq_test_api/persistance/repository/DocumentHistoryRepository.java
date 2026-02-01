package com.labzin.document_service_itq_test_api.persistance.repository;

import com.labzin.document_service_itq_test_api.persistance.entity.DocumentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DocumentHistoryRepository extends JpaRepository<DocumentHistory, UUID> {
    List<DocumentHistory> findByDocumentId(UUID documentId);
}

