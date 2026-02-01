package com.labzin.document_service_itq_test_api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Builder
public class GetDocumentsDtoList {
    private List<GetDocumentResponse> documents;
    private int page;
    private int size;
    private long total;
    private int totalPages;
}
