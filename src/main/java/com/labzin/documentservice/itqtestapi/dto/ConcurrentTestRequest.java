package com.labzin.documentservice.itqtestapi.dto;

public record ConcurrentTestRequest(
        int threads, int attempts
) {
}
