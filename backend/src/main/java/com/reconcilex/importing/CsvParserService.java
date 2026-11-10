package com.reconcilex.importing;

import com.reconcilex.importing.dto.CsvRowData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class CsvParserService {

    private final CsvHeaderValidator headerValidator;

    public CsvParserService(CsvHeaderValidator headerValidator) {
        this.headerValidator = headerValidator;
    }

    public List<CsvRowData> parse(MultipartFile file) throws IOException {

        List<CsvRowData> rows = new ArrayList<>();

        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                file.getInputStream(),
                                StandardCharsets.UTF_8));

                CSVParser parser = CSVFormat.DEFAULT.builder()
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .setIgnoreEmptyLines(true)
                        .get()
                        .parse(reader)) {

            List<String> headers = parser.getHeaderNames();

            // Remove UTF-8 BOM and normalize headers.
            List<String> normalizedHeaders = headers.stream()
                    .map(header -> header.replace("\uFEFF", "")
                            .trim()
                            .toLowerCase(Locale.ROOT))
                    .toList();

            headerValidator.validate(normalizedHeaders);

            for (CSVRecord record : parser) {

                Map<String, String> values = new LinkedHashMap<>();

                for (int i = 0; i < headers.size(); i++) {
                    values.put(
                            normalizedHeaders.get(i),
                            record.get(i));
                }

                rows.add(new CsvRowData(
                        Math.toIntExact(record.getRecordNumber() + 1),
                        values));
            }
        }

        return rows;
    }
}