package com.labzin.documentservice.itqtestapi.persistance.repository;

import com.labzin.documentservice.itqtestapi.persistance.entity.Document;
import com.labzin.documentservice.itqtestapi.persistance.enums.DocumentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID>, JpaSpecificationExecutor<Document> {

    @Override
    Optional<Document> findById(UUID uuid);

    Page<Document> findByStatus(DocumentStatus status, Pageable pageable);
}
