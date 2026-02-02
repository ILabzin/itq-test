package com.labzin.document_service_itq_test_api.service;

import com.labzin.document_service_itq_test_api.dto.AgreementResponse;
import com.labzin.document_service_itq_test_api.dto.ApprovalRequest;
import com.labzin.document_service_itq_test_api.dto.ApprovalResponse;
import com.labzin.document_service_itq_test_api.dto.CreateDocumentRequest;

import com.labzin.document_service_itq_test_api.dto.CreateDocumentResponse;
import com.labzin.document_service_itq_test_api.dto.DocumentHistoryResponse;
import com.labzin.document_service_itq_test_api.dto.DocumentSearchCriteria;
import com.labzin.document_service_itq_test_api.dto.GetDocumentResponse;
import com.labzin.document_service_itq_test_api.dto.GetDocumentsDtoList;
import com.labzin.document_service_itq_test_api.exception.NotFoundException;
import com.labzin.document_service_itq_test_api.exception.ValidationException;
import com.labzin.document_service_itq_test_api.mapper.DocumentHistoryMapper;
import com.labzin.document_service_itq_test_api.mapper.DocumentMapper;
import com.labzin.document_service_itq_test_api.persistance.entity.ApprovalRegistry;
import com.labzin.document_service_itq_test_api.persistance.entity.Document;
import com.labzin.document_service_itq_test_api.persistance.entity.DocumentHistory;
import com.labzin.document_service_itq_test_api.persistance.enums.Action;
import com.labzin.document_service_itq_test_api.persistance.enums.DocumentStatus;
import com.labzin.document_service_itq_test_api.persistance.enums.StatusChangeResult;
import com.labzin.document_service_itq_test_api.persistance.repository.ApprovalRegistryRepository;
import com.labzin.document_service_itq_test_api.persistance.repository.DocumentHistoryRepository;
import com.labzin.document_service_itq_test_api.persistance.repository.DocumentRepository;

import com.labzin.document_service_itq_test_api.persistance.repository.specification.Specifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;



@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentHistoryRepository documentHistoryRepository;
    private final DocumentHistoryMapper documentHistoryMapper;
    private final DocumentMapper documentMapper;
    private final DocumentApprovalService documentApprovalService;

    @Transactional
    public CreateDocumentResponse createDocument(CreateDocumentRequest request) {

        Document document = Document.builder()
                .title(request.title())
                .author(request.author())
                .status(DocumentStatus.DRAFT)
                .build();

        Document createdDocument = documentRepository.save(document);

        return CreateDocumentResponse.created(createdDocument.getId());
    }

    @Transactional(readOnly = true)
    public GetDocumentResponse getDocument(UUID id) {

        if (id == null) {
            throw new ValidationException("Document ID cannot be null");
        }

        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Document not found"));
        List<DocumentHistory> historyList = documentHistoryRepository.findByDocumentId(id);
        List<DocumentHistoryResponse> historyResponses = documentHistoryMapper.toResponseList(historyList);

        return GetDocumentResponse.builder()
                .id(document.getId())
                .documentNumber(document.getDocumentNumber())
                .title(document.getTitle())
                .author(document.getAuthor())
                .status(document.getStatus())
                .createdAt(document.getCreatedAt())
                .updatedAt(document.getUpdatedAt())
                .history(historyResponses)
                .build();
    }

    @Transactional(readOnly = true)
    public GetDocumentsDtoList getDocuments(int page, int size, String sortBy, String sortDir, List<UUID> uuids) {

        Pageable pageable;
        Page<Document> pageResult;

        if (sortBy != null) {
            Sort.Direction direction = "desc".equalsIgnoreCase(sortDir)
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;

            Sort sort = Sort.by(direction, sortBy);

            pageable = PageRequest.of(page - 1, size, sort);
        } else {
            pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        }

        if (uuids.contains(null)) {
            throw new ValidationException("UUIDs contain null values");
        }

        if (!uuids.isEmpty()) {

            Specification<Document> spec = Specifications.documentIds(uuids);
            pageResult = documentRepository.findAll(spec, pageable);
        } else {
            pageResult = documentRepository.findAll(pageable);
        }

        return GetDocumentsDtoList.builder()
                .documents(documentMapper.toGetDocumentResponseList(pageResult.getContent()))
                .page(page)
                .size(size)
                .total(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .build();
    }

    @Transactional
    public AgreementResponse agreement(List<UUID> ids) {

        Map<UUID, StatusChangeResult> results = new HashMap<>();

        for (UUID id : ids) {
            StatusChangeResult status = processDocumentStatusChange(id);
            results.put(id, status);
        }

        return new AgreementResponse(results);
    }


    public StatusChangeResult processDocumentStatusChange(UUID id) {

            Document document = documentRepository.findById(id).orElse(null);

            if (document == null) {
                return StatusChangeResult.NOT_FOUND;
            }

            if (document.getStatus() != DocumentStatus.DRAFT) {
                return StatusChangeResult.CONFLICT;
            }

            document.setStatus(DocumentStatus.SUBMITTED);
            return StatusChangeResult.SUCCESS;
    }

    @Transactional
    public ApprovalResponse approve(List<UUID> ids) {

        Map<UUID, StatusChangeResult> results = new HashMap<>();

        for (UUID id : ids) {

            StatusChangeResult result = documentApprovalService.processDocumentApproval(id);
            results.put(id, result);

        }

        return new ApprovalResponse(results);
    }

    public Page<GetDocumentResponse> searchDocuments(
            DocumentSearchCriteria criteria,
            Pageable pageable) {

        Specification<Document> spec = Specifications.getDocumentSpec(
                criteria.status(),
                criteria.author(),
                criteria.createdFrom(),
                criteria.createdTo()
        );

        Page<Document> documents = documentRepository.findAll(spec, pageable);

        return documents.map(documentMapper::toGetDocumentResponse);
    }
}

