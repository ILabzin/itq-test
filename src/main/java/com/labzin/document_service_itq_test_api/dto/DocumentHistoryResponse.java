package com.labzin.document_service_itq_test_api.dto;

import com.labzin.document_service_itq_test_api.persistance.enums.Action;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record DocumentHistoryResponse(
        Action action,
        String actionBy,
        LocalDateTime actionAt,
        String comment
) {}
