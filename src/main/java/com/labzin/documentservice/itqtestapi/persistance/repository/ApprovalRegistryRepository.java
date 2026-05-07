package com.labzin.documentservice.itqtestapi.persistance.repository;

import com.labzin.documentservice.itqtestapi.persistance.entity.ApprovalRegistry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApprovalRegistryRepository  extends JpaRepository<ApprovalRegistry, UUID> {
}
