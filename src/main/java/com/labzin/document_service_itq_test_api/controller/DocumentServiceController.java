package com.labzin.document_service_itq_test_api.controller;

import com.labzin.document_service_itq_test_api.dto.AgreementRequest;
import com.labzin.document_service_itq_test_api.dto.AgreementResponse;
import com.labzin.document_service_itq_test_api.dto.ApprovalRequest;
import com.labzin.document_service_itq_test_api.dto.ApprovalResponse;
import com.labzin.document_service_itq_test_api.dto.ConcurrentTestRequest;
import com.labzin.document_service_itq_test_api.dto.ConcurrentTestResponse;
import com.labzin.document_service_itq_test_api.dto.CreateDocumentRequest;
import com.labzin.document_service_itq_test_api.dto.CreateDocumentResponse;
import com.labzin.document_service_itq_test_api.dto.DocumentSearchCriteria;
import com.labzin.document_service_itq_test_api.dto.GetDocumentResponse;
import com.labzin.document_service_itq_test_api.dto.GetDocumentsDtoList;
import com.labzin.document_service_itq_test_api.persistance.entity.Document;
import com.labzin.document_service_itq_test_api.service.ConcurrentTestService;
import com.labzin.document_service_itq_test_api.service.DocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.views.DocumentView;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentServiceController {

    private final DocumentService documentService;
    private final ConcurrentTestService concurrentTestService;

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

    @GetMapping("/search")
    public ResponseEntity<Page<GetDocumentResponse>> searchDocuments(
            DocumentSearchCriteria criteria,
            @PageableDefault(
                    size = 20,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {

        Page<GetDocumentResponse> result = documentService.searchDocuments(criteria, pageable);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/concurrent-test")
    public ConcurrentTestResponse ConcurrentTest(
            @PathVariable UUID id,
            @RequestBody ConcurrentTestRequest request) {

        return concurrentTestService.runConcurrentTest(id, request.threads(), request.attempts());
    }
}
