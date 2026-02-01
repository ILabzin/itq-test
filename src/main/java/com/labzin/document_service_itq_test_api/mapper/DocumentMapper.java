package com.labzin.document_service_itq_test_api.mapper;

import com.labzin.document_service_itq_test_api.dto.GetDocumentResponse;
import com.labzin.document_service_itq_test_api.persistance.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = DocumentHistoryMapper.class)
public interface DocumentMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "documentNumber", target = "documentNumber")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "history", target = "history")
    GetDocumentResponse toGetDocumentResponse(Document document);

    List<GetDocumentResponse> toGetDocumentResponseList(List<Document> documents);
}
