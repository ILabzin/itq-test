package com.labzin.document_service_itq_test_api.controller;

import com.labzin.document_service_itq_test_api.dto.AgreementRequest;
import com.labzin.document_service_itq_test_api.dto.AgreementResponse;
import com.labzin.document_service_itq_test_api.dto.ApprovalRequest;
import com.labzin.document_service_itq_test_api.dto.ApprovalResponse;
import com.labzin.document_service_itq_test_api.dto.CreateDocumentRequest;
import com.labzin.document_service_itq_test_api.dto.CreateDocumentResponse;
import com.labzin.document_service_itq_test_api.dto.GetDocumentResponse;
import com.labzin.document_service_itq_test_api.dto.GetDocumentsDtoList;
import com.labzin.document_service_itq_test_api.service.DocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentServiceController {

    private final DocumentService documentService;

    @PostMapping("/create")
    public ResponseEntity<CreateDocumentResponse> createDocument(@RequestBody CreateDocumentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(documentService.createDocument(request));
    }

    @GetMapping("/{id}")
    public GetDocumentResponse getDocument(@PathVariable UUID id) {
        return documentService.getDocument(id);
    }

    @GetMapping("/listDocuments")
    public GetDocumentsDtoList getDocuments(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDir,
            @RequestParam(required = false) List<UUID> uuids
    ) {
        return documentService.getDocuments(page, size, sortBy, sortDir, uuids);
    }

    @PostMapping("/agreement")
    public ResponseEntity<AgreementResponse> agreement(
            @Valid @RequestBody AgreementRequest request) {

        AgreementResponse response = documentService.agreement(request.ids());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/approve")
    public ResponseEntity<ApprovalResponse> approve(
            @Valid @RequestBody ApprovalRequest request) {

        ApprovalResponse response = documentService.approve(request.ids());
        return ResponseEntity.ok(response);
    }
}
