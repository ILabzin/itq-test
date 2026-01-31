package com.labzin.document_service_itq_test_api.service;

import com.labzin.document_service_itq_test_api.dto.CreateDocumentRequest;

import com.labzin.document_service_itq_test_api.dto.CreateDocumentResponse;
import com.labzin.document_service_itq_test_api.dto.GetDocumentResponse;
import com.labzin.document_service_itq_test_api.exception.NotFoundException;
import com.labzin.document_service_itq_test_api.persistance.entity.Document;
import com.labzin.document_service_itq_test_api.persistance.enums.DocumentStatus;
import com.labzin.document_service_itq_test_api.persistance.repository.DocumentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;

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

    public GetDocumentResponse getDocument(UUID id) {

        return null;
    }
}

