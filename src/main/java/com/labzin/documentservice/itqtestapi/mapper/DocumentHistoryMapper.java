package com.labzin.documentservice.itqtestapi.mapper;

import com.labzin.documentservice.itqtestapi.dto.DocumentHistoryResponse;
import com.labzin.documentservice.itqtestapi.persistance.entity.DocumentHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentHistoryMapper {

    @Mapping(source = "action", target = "action")
    @Mapping(source = "actionBy", target = "actionBy")
    @Mapping(source = "actionAt", target = "actionAt")
    @Mapping(source = "comment", target = "comment")
    DocumentHistoryResponse toResponse(DocumentHistory entity);

    List<DocumentHistoryResponse> toResponseList(List<DocumentHistory> entities);
}
