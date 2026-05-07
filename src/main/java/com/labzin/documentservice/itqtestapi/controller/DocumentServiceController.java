package com.labzin.documentservice.itqtestapi.controller;

import com.labzin.documentservice.itqtestapi.dto.AgreementRequest;
import com.labzin.documentservice.itqtestapi.dto.AgreementResponse;
import com.labzin.documentservice.itqtestapi.dto.ApprovalRequest;
import com.labzin.documentservice.itqtestapi.dto.ApprovalResponse;
import com.labzin.documentservice.itqtestapi.dto.ConcurrentTestRequest;
import com.labzin.documentservice.itqtestapi.dto.ConcurrentTestResponse;
import com.labzin.documentservice.itqtestapi.dto.CreateDocumentRequest;
import com.labzin.documentservice.itqtestapi.dto.CreateDocumentResponse;
import com.labzin.documentservice.itqtestapi.dto.DocumentSearchCriteria;
import com.labzin.documentservice.itqtestapi.dto.GetDocumentResponse;
import com.labzin.documentservice.itqtestapi.dto.GetDocumentsDtoList;
import com.labzin.documentservice.itqtestapi.service.ConcurrentTestService;
import com.labzin.documentservice.itqtestapi.service.DocumentService;
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
