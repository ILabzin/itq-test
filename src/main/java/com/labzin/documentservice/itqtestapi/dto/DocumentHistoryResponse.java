package com.labzin.documentservice.itqtestapi.dto;

import com.labzin.documentservice.itqtestapi.persistance.enums.Action;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record DocumentHistoryResponse(
        Action action,
        String actionBy,
        LocalDateTime actionAt,
        String comment
) {}
