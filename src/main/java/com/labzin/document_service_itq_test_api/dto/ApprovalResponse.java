package com.labzin.document_service_itq_test_api.dto;

import com.labzin.document_service_itq_test_api.persistance.enums.StatusChangeResult;

import java.util.Map;
import java.util.UUID;


public record ApprovalResponse(
        Map<UUID, StatusChangeResult> results
) {}
