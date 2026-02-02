package com.labzin.document_service_itq_test_api.dto;

public record ConcurrentTestRequest(
        int threads, int attempts
) {
}
