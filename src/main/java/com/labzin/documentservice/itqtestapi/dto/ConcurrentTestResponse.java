package com.labzin.documentservice.itqtestapi.dto;

import com.labzin.documentservice.itqtestapi.persistance.enums.DocumentStatus;

public record ConcurrentTestResponse(
        int allTryes, int sucsess, int conflict, DocumentStatus status, int historyCreated
) {
}
