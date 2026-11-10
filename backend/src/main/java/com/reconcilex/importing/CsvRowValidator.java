package com.reconcilex.importing;

import com.reconcilex.importing.dto.CsvRowData;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class CsvRowValidator {

    private static final Set<String> ALLOWED_TRANSACTION_TYPES = Set.of(
            "PAYMENT",
            "REFUND",
            "CHARGEBACK",
            "SETTLEMENT");

    public List<String> validate(CsvRowData row) {

        List<String> errors = new ArrayList<>();
        Map<String, String> values = row.values();

        validateAmount(values.get("amount"), errors);
        validateCurrency(values.get("currency"), errors);
        validateTransactionType(values.get("transaction_type"), errors);
        validateOccurredAt(values.get("occurred_at"), errors);

        return errors;
    }

    private void validateAmount(String value, List<String> errors) {

        if (value == null || value.isBlank()) {
            errors.add("Amount is required.");
            return;
        }

        try {
            BigDecimal amount = new BigDecimal(value.trim());

            if (amount.signum() <= 0) {
                errors.add("Amount must be greater than zero.");
            }

            if (amount.scale() > 4 || amount.precision() - amount.scale() > 15) {
                errors.add("Amount must have at most 15 integer digits and 4 decimal places.");
            }

        } catch (NumberFormatException exception) {
            errors.add("Amount must be a valid number.");
        }
    }

    private void validateCurrency(String value, List<String> errors) {

        if (value == null || !value.trim().matches("[A-Z]{3}")) {
            errors.add("Currency must be a valid three-letter uppercase code.");
        }
    }

    private void validateTransactionType(String value, List<String> errors) {

        if (value == null || !ALLOWED_TRANSACTION_TYPES.contains(value.trim().toUpperCase())) {
            errors.add("Transaction type must be PAYMENT, REFUND, CHARGEBACK or SETTLEMENT.");
        }
    }

    private void validateOccurredAt(String value, List<String> errors) {

        if (value == null || value.isBlank()) {
            errors.add("Transaction date and time are required.");
            return;
        }

        try {
            OffsetDateTime occurredAt = OffsetDateTime.parse(value.trim());

            if (occurredAt.isAfter(OffsetDateTime.now())) {
                errors.add("Transaction date and time cannot be in the future.");
            }

        } catch (DateTimeParseException exception) {
            errors.add("Transaction date and time must use ISO 8601 format with a timezone offset.");
        }
    }
}