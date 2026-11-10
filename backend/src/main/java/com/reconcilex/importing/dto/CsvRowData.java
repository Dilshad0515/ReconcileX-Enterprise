package com.reconcilex.importing.dto;

import java.util.Map;

public record CsvRowData(
        int rowNumber,
        Map<String, String> values) {
}