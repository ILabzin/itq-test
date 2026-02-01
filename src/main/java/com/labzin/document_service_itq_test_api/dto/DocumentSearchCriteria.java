package com.labzin.document_service_itq_test_api.dto;

import com.labzin.document_service_itq_test_api.persistance.enums.DocumentStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record DocumentSearchCriteria(
        DocumentStatus status,
        String author,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime createdFrom,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime createdTo
) {}
