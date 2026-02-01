package com.labzin.document_service_itq_test_api.dto;

import jakarta.validation.constraints.Size;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;
import java.util.UUID;

public record AgreementRequest(
        @Size(min = 1, max = 1000, message = "Must contain 1 to 1000 IDs")
        List<UUID> ids
) {}
