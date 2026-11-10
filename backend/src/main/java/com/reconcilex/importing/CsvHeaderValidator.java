package com.reconcilex.importing;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CsvHeaderValidator {

    private static final Set<String> REQUIRED_HEADERS = Set.of(
            "source_transaction_ref",
            "amount",
            "currency",
            "transaction_type",
            "transaction_status",
            "occurred_at");

    public void validate(List<String> headers) {

        Set<String> normalizedHeaders = headers.stream()
                .map(header -> header.trim().toLowerCase(Locale.ROOT))
                .collect(Collectors.toSet());

        Set<String> missingHeaders = REQUIRED_HEADERS.stream()
                .filter(header -> !normalizedHeaders.contains(header))
                .collect(Collectors.toSet());

        if (!missingHeaders.isEmpty()) {
            throw new IllegalArgumentException(
                    "CSV file is missing required columns: " + missingHeaders);
        }
    }
}