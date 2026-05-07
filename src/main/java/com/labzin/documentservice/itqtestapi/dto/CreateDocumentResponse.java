package com.labzin.documentservice.itqtestapi.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDocumentResponse {
    private String message;
    private UUID documentUUID;

    public static CreateDocumentResponse created(UUID documentUUID) {
        return new CreateDocumentResponse(
                "Документ создан",
                documentUUID
        );
    }
}
