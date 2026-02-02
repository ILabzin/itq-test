package com.labzin.document_service_itq_test_api.dto;

import com.labzin.document_service_itq_test_api.persistance.enums.DocumentStatus;

public record ConcurrentTestResponse(
        int allTryes, int sucsess, int conflict, DocumentStatus status, int historyCreated
) {
}
