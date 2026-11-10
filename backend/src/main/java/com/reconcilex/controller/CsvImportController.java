package com.reconcilex.controller;

import com.reconcilex.entity.ImportBatch;
import com.reconcilex.importing.CsvImportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/imports")
public class CsvImportController {

    private final CsvImportService csvImportService;

    public CsvImportController(CsvImportService csvImportService) {
        this.csvImportService = csvImportService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> uploadCsv(
            @RequestParam Long dataSourceId,
            @RequestParam("file") MultipartFile file) throws IOException {

        ImportBatch batch = csvImportService.importCsv(dataSourceId, file);

        Map<String, Object> response = Map.of(
                "message", "CSV imported successfully.",
                "batchId", batch.getId(),
                "fileName", batch.getFileName(),
                "totalRows", batch.getTotalRows(),
                "successfulRows", batch.getSuccessfulRows(),
                "failedRows", batch.getFailedRows(),
                "status", batch.getBatchStatus());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}