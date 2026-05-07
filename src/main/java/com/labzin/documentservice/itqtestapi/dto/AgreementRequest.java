package com.labzin.documentservice.itqtestapi.dto;

import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record AgreementRequest(
        @Size(min = 1, max = 1000, message = "Must contain 1 to 1000 IDs")
        List<UUID> ids
) {}
