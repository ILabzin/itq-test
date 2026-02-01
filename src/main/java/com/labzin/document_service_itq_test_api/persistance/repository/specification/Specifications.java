package com.labzin.document_service_itq_test_api.persistance.repository.specification;

import com.labzin.document_service_itq_test_api.persistance.entity.Document;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public class Specifications {

    public static Specification<Document> documentIds(List<UUID> ids) {
        return (root, query, criteriaBuilder) -> {
            if (ids == null || ids.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.get("id").in(ids);
        };
    }
}