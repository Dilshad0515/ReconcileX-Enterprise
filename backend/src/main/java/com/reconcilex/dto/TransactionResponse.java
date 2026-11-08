
package com.reconcilex.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransactionResponse(

        Long id,

        String sourceTransactionRef,

        BigDecimal amount,

        String currency,

        String transactionType,

        String transactionStatus,

        OffsetDateTime occurredAt,

        String sourceName,

        String fileName

) {
}
