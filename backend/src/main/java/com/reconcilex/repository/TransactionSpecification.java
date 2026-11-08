
package com.reconcilex.repository;

import com.reconcilex.entity.SourceTransaction;

import org.springframework.data.jpa.domain.Specification;

public class TransactionSpecification {

    private TransactionSpecification() {
    }

    public static Specification<SourceTransaction> hasTransactionType(
            String transactionType) {

        return (root, query, criteriaBuilder) -> transactionType == null || transactionType.isBlank()
                ? null
                : criteriaBuilder.equal(
                        criteriaBuilder.upper(root.get("transactionType")),
                        transactionType.trim().toUpperCase());
    }

    public static Specification<SourceTransaction> hasTransactionStatus(
            String transactionStatus) {

        return (root, query, criteriaBuilder) -> transactionStatus == null || transactionStatus.isBlank()
                ? null
                : criteriaBuilder.equal(
                        criteriaBuilder.upper(root.get("transactionStatus")),
                        transactionStatus.trim().toUpperCase());
    }

    public static Specification<SourceTransaction> hasReference(
            String reference) {

        return (root, query, criteriaBuilder) -> reference == null || reference.isBlank()
                ? null
                : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("sourceTransactionRef")),
                        "%" + reference.trim().toLowerCase() + "%");
    }
}
