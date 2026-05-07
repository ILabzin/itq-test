package com.labzin.documentservice.itqtestapi.dto;

import com.labzin.documentservice.itqtestapi.persistance.enums.StatusChangeResult;

import java.util.Map;
import java.util.UUID;

public record AgreementResponse(Map<UUID, StatusChangeResult> results) {}
