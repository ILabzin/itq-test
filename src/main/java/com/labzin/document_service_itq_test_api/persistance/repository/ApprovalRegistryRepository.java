package com.labzin.document_service_itq_test_api.persistance.repository;

import com.labzin.document_service_itq_test_api.persistance.entity.ApprovalRegistry;
import com.labzin.document_service_itq_test_api.persistance.entity.DocumentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApprovalRegistryRepository  extends JpaRepository<ApprovalRegistry, UUID> {
}
