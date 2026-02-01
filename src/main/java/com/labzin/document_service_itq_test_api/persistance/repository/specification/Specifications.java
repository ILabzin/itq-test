package com.labzin.document_service_itq_test_api.persistance.repository.specification;

import com.labzin.document_service_itq_test_api.persistance.entity.Document;
import com.labzin.document_service_itq_test_api.persistance.enums.DocumentStatus;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
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
    public static Specification<Document> hasStatus(@Nullable DocumentStatus status) {
        return (root, query, cb) -> status == null
                ? cb.conjunction()
                : cb.equal(root.get("status"), status);
    }

    public static Specification<Document> hasAuthor(@Nullable String author) {
        return (root, query, cb) -> author == null || author.isEmpty()
                ? cb.conjunction()
                : cb.equal(root.get("author"), author);
    }

    public static Specification<Document> createdBetween(
            @Nullable LocalDateTime from,
            @Nullable LocalDateTime to) {
        return (root, query, cb) -> {
            if (from == null && to == null) {
                return cb.conjunction();
            }
            if (from != null && to != null) {
                return cb.between(root.get("createdAt"), from, to);
            }
            if (from != null) {
                return cb.greaterThanOrEqualTo(root.get("createdAt"), from);
            }
            return cb.lessThanOrEqualTo(root.get("createdAt"), to);
        };
    }

    public static Specification<Document> getDocumentSpec(
            @Nullable DocumentStatus status,
            @Nullable String author,
            @Nullable LocalDateTime from,
            @Nullable LocalDateTime to) {
        return Specification.where(hasStatus(status))
                .and(hasAuthor(author))
                .and(createdBetween(from, to));
    }
}