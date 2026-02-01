package com.labzin.document_service_itq_test_api.persistance.repository;

import com.labzin.document_service_itq_test_api.persistance.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID>, JpaSpecificationExecutor<Document> {

    @Override
    Optional<Document> findById(UUID uuid);
}
