package com.labzin.document_service_itq_test_api.persistance.repository;

import com.labzin.document_service_itq_test_api.persistance.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {

    @Override
    Optional<Document> findById(UUID uuid);
}
