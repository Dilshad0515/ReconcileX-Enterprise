
package com.reconcilex.controller;

import com.reconcilex.dto.TransactionResponse;
import com.reconcilex.service.TransactionService;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public Page<TransactionResponse> getTransactions(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "occurredAt") String sortBy,

            @RequestParam(defaultValue = "desc") String sortDir,

            @RequestParam(required = false) String transactionType,

            @RequestParam(required = false) String transactionStatus,

            @RequestParam(required = false) String reference) {

        return transactionService.getTransactions(
                page,
                size,
                sortBy,
                sortDir,
                transactionType,
                transactionStatus,
                reference);
    }
}
