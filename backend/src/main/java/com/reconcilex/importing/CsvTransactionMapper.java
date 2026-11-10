package com.reconcilex.importing;

import com.reconcilex.entity.ImportBatch;
import com.reconcilex.entity.SourceTransaction;
import com.reconcilex.importing.dto.CsvRowData;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@Component
public class CsvTransactionMapper {

    public SourceTransaction map(
            CsvRowData row,
            ImportBatch importBatch) {

        Map<String, String> values = row.values();

        SourceTransaction transaction = new SourceTransaction(importBatch);

        String reference = values.get("source_transaction_ref");

        transaction.setSourceTransactionRef(
                reference == null || reference.isBlank()
                        ? null
                        : reference.trim());

        transaction.setAmount(
                new BigDecimal(values.get("amount").trim()));

        transaction.setCurrency(
                values.get("currency").trim().toUpperCase(Locale.ROOT));

        transaction.setTransactionType(
                values.get("transaction_type").trim().toUpperCase(Locale.ROOT));

        String status = values.get("transaction_status");

        transaction.setTransactionStatus(
                status == null || status.isBlank()
                        ? null
                        : status.trim());

        transaction.setOccurredAt(
                OffsetDateTime.parse(values.get("occurred_at").trim()));

        Map<String, Object> rawData = new LinkedHashMap<>();
        rawData.putAll(values);
        rawData.put("_csv_row_number", row.rowNumber());

        transaction.setRawData(rawData);

        return transaction;
    }
}