package com.labzin.document_service_itq_test_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;
import java.util.List;

public record ApprovalRequest(
        @Size(min = 1, max = 1000, message = "Must contain 1 to 1000 IDs")
        List<UUID> ids
) {}
