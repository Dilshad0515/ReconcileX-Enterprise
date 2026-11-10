package com.reconcilex.importing;

import com.reconcilex.importing.dto.CsvRowData;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CsvRowValidatorTest {

    private final CsvRowValidator validator = new CsvRowValidator();

    @Test
    void shouldAcceptValidTransaction() {

        CsvRowData row = new CsvRowData(
                2,
                Map.of(
                        "source_transaction_ref", "TXN1001",
                        "amount", "1000.00",
                        "currency", "INR",
                        "transaction_type", "PAYMENT",
                        "transaction_status", "SUCCESS",
                        "occurred_at", "2026-11-07T10:30:00+05:30"));

        var errors = validator.validate(row);

        assertTrue(errors.isEmpty());
    }

    @Test
    void shouldRejectInvalidAmount() {

        CsvRowData row = new CsvRowData(
                3,
                Map.of(
                        "amount", "-500",
                        "currency", "INR",
                        "transaction_type", "PAYMENT",
                        "occurred_at", "2026-11-07T10:30:00+05:30"));

        var errors = validator.validate(row);

        assertTrue(errors.contains("Amount must be greater than zero."));
    }

    @Test
    void shouldRejectInvalidCurrencyAndTransactionType() {

        CsvRowData row = new CsvRowData(
                4,
                Map.of(
                        "amount", "500",
                        "currency", "IN",
                        "transaction_type", "UNKNOWN",
                        "occurred_at", "2026-11-07T10:30:00+05:30"));

        var errors = validator.validate(row);

        assertEquals(2, errors.size());
    }
}