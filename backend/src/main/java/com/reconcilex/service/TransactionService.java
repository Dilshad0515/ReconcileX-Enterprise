package com.reconcilex.service;

import com.reconcilex.dto.TransactionResponse;
import com.reconcilex.entity.SourceTransaction;
import com.reconcilex.repository.TransactionRepository;
import com.reconcilex.repository.TransactionSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

@Service
public class TransactionService {

        private final TransactionRepository transactionRepository;

        // Constructor injection
        public TransactionService(
                        TransactionRepository transactionRepository) {

                this.transactionRepository = transactionRepository;
        }

        @Transactional(readOnly = true)
        public List<TransactionResponse> getAllTransactions() {

                List<SourceTransaction> transactions = transactionRepository.findAll();

                return transactions.stream()
                                .map(this::convertToResponse)
                                .toList();
        }

        private TransactionResponse convertToResponse(
                        SourceTransaction transaction) {

                return new TransactionResponse(
                                transaction.getId(),
                                transaction.getSourceTransactionRef(),
                                transaction.getAmount(),
                                transaction.getCurrency(),
                                transaction.getTransactionType(),
                                transaction.getTransactionStatus(),
                                transaction.getOccurredAt(),
                                transaction.getImportBatch()
                                                .getDataSource()
                                                .getSourceName(),
                                transaction.getImportBatch()
                                                .getFileName());
        }

        @Transactional(readOnly = true)
        public Page<TransactionResponse> getTransactions(
                        int page,
                        int size,
                        String sortBy,
                        String sortDir,
                        String transactionType,
                        String transactionStatus,
                        String reference) {

                if (page < 0) {
                        throw new IllegalArgumentException("Page number cannot be negative");
                }

                if (size < 1 || size > 100) {
                        throw new IllegalArgumentException("Page size must be between 1 and 100");
                }

                // Only allow supported entity fields for sorting.
                List<String> allowedSortFields = List.of(
                                "id",
                                "amount",
                                "occurredAt",
                                "transactionType",
                                "transactionStatus");

                if (!allowedSortFields.contains(sortBy)) {
                        throw new IllegalArgumentException("Unsupported sort field: " + sortBy);
                }

                Sort.Direction direction = "asc".equalsIgnoreCase(sortDir)
                                ? Sort.Direction.ASC
                                : "desc".equalsIgnoreCase(sortDir)
                                                ? Sort.Direction.DESC
                                                : null;

                if (direction == null) {
                        throw new IllegalArgumentException("Sort direction must be asc or desc");
                }

                Pageable pageable = PageRequest.of(
                                page,
                                size,
                                Sort.by(direction, sortBy));

                Specification<SourceTransaction> specification = Specification.allOf(
                                TransactionSpecification.hasTransactionType(transactionType),
                                TransactionSpecification.hasTransactionStatus(transactionStatus),
                                TransactionSpecification.hasReference(reference));

                return transactionRepository.findAll(specification, pageable)
                                .map(this::convertToResponse);
        }

}
