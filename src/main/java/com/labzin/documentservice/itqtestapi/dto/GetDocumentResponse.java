package com.labzin.documentservice.itqtestapi.dto;

import com.labzin.documentservice.itqtestapi.persistance.enums.DocumentStatus;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record GetDocumentResponse(
        UUID id,
        Long documentNumber,
        String title,
        String author,
        DocumentStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<DocumentHistoryResponse> history
) {}
