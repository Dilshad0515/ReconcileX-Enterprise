
package com.reconcilex.service;

import com.reconcilex.dto.TransactionResponse;
import com.reconcilex.entity.SourceTransaction;
import com.reconcilex.repository.TransactionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
