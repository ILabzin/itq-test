package com.labzin.document_service_itq_test_api.controller;

import com.labzin.document_service_itq_test_api.dto.CreateDocumentRequest;
import com.labzin.document_service_itq_test_api.dto.CreateDocumentResponse;
import com.labzin.document_service_itq_test_api.dto.GetDocumentResponse;
import com.labzin.document_service_itq_test_api.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentServiceController {

    private final DocumentService documentService;

    @PostMapping("/create")
    public CreateDocumentResponse createDocument(@RequestBody CreateDocumentRequest request) {
        return documentService.createDocument(request);
    }

    @GetMapping("/{id}")
    public GetDocumentResponse getDocument(@PathVariable UUID id) {
        return documentService.getDocument(id);
    }
}
