package com.labzin.documentservice.itqtestapi.dto;

import com.labzin.documentservice.itqtestapi.persistance.enums.DocumentStatus;
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
