package com.labzin.document_service_itq_test_api.dto;

import com.labzin.document_service_itq_test_api.persistance.enums.DocumentStatus;
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
