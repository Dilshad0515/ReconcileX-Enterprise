
package com.reconcilex.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransactionImportRequest(

        @NotBlank(message = "Transaction reference is required") @Size(max = 150, message = "Transaction reference cannot exceed 150 characters") String sourceTransactionRef,

        @NotNull(message = "Transaction amount is required") @Positive(message = "Transaction amount must be greater than zero") @Digits(integer = 15, fraction = 4, message = "Amount can have up to 15 integer digits and 4 decimal places") BigDecimal amount,

        @NotBlank(message = "Currency is required") @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a valid three-letter uppercase code") String currency,

        @NotBlank(message = "Transaction type is required") @Pattern(regexp = "PAYMENT|REFUND|CHARGEBACK|SETTLEMENT", message = "Transaction type must be PAYMENT, REFUND, CHARGEBACK or SETTLEMENT") String transactionType,

        @Size(max = 30, message = "Transaction status cannot exceed 30 characters") String transactionStatus,

        @NotNull(message = "Transaction date and time are required") @PastOrPresent(message = "Transaction date cannot be in the future") OffsetDateTime occurredAt

) {
}
