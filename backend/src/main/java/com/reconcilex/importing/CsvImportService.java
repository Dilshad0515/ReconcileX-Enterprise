package com.reconcilex.importing;

import com.reconcilex.entity.*;
import com.reconcilex.importing.dto.CsvRowData;
import com.reconcilex.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CsvImportService {

    private final DataSourceRepository dataSourceRepository;
    private final ImportBatchRepository importBatchRepository;
    private final TransactionRepository transactionRepository;
    private final ImportRowErrorRepository importRowErrorRepository;
    private final CsvParserService csvParserService;
    private final CsvHeaderValidator csvHeaderValidator;
    private final CsvRowValidator csvRowValidator;
    private final CsvTransactionMapper csvTransactionMapper;

    public CsvImportService(
            DataSourceRepository dataSourceRepository,
            ImportBatchRepository importBatchRepository,
            TransactionRepository transactionRepository,
            ImportRowErrorRepository importRowErrorRepository,
            CsvParserService csvParserService,
            CsvHeaderValidator csvHeaderValidator,
            CsvRowValidator csvRowValidator,
            CsvTransactionMapper csvTransactionMapper) {
        this.dataSourceRepository = dataSourceRepository;
        this.importBatchRepository = importBatchRepository;
        this.transactionRepository = transactionRepository;
        this.importRowErrorRepository = importRowErrorRepository;
        this.csvParserService = csvParserService;
        this.csvHeaderValidator = csvHeaderValidator;
        this.csvRowValidator = csvRowValidator;
        this.csvTransactionMapper = csvTransactionMapper;
    }

    @Transactional
    public ImportBatch importCsv(Long dataSourceId, MultipartFile file)
            throws IOException {

        // 1. Validate uploaded file.
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Please upload a non-empty CSV file.");
        }

        String fileName = file.getOriginalFilename();

        if (fileName == null ||
                !fileName.toLowerCase().endsWith(".csv")) {
            throw new IllegalArgumentException("Only CSV files are supported.");
        }

        // 2. Find registered data source.
        DataSource dataSource = dataSourceRepository.findById(dataSourceId)
                .orElseThrow(() -> new IllegalArgumentException("Data source not found."));

        if (!Boolean.TRUE.equals(dataSource.getActive())) {
            throw new IllegalArgumentException("Selected data source is inactive.");
        }

        // 3. Calculate SHA-256 file hash.
        String fileHash = calculateFileHash(file);

        // 4. Parse CSV.
        List<CsvRowData> rows = csvParserService.parse(file);

        // 5. Create import batch.
        ImportBatch batch = new ImportBatch(
                dataSource,
                fileName,
                fileHash);

        batch.setBatchStatus("PROCESSING");
        batch.setTotalRows(rows.size());
        batch.setSuccessfulRows(0);
        batch.setFailedRows(0);

        batch = importBatchRepository.save(batch);

        int successfulRows = 0;
        int failedRows = 0;

        // 6. Validate and process individual rows.
        for (CsvRowData row : rows) {

            List<String> errors = csvRowValidator.validate(row);

            if (!errors.isEmpty()) {

                ImportRowError rowError = new ImportRowError(
                        batch,
                        row.rowNumber(),
                        "ROW_VALIDATION_FAILED",
                        String.join(" ", errors));

                rowError.setRawRowData(toObjectMap(row.values()));

                importRowErrorRepository.save(rowError);

                failedRows++;
                continue;
            }

            SourceTransaction transaction = csvTransactionMapper.map(row, batch);

            transactionRepository.save(transaction);

            successfulRows++;
        }

        // 7. Update batch statistics.
        batch.setSuccessfulRows(successfulRows);
        batch.setFailedRows(failedRows);

        batch.setBatchStatus(
                failedRows == rows.size() && !rows.isEmpty()
                        ? "FAILED"
                        : "COMPLETED");

        return importBatchRepository.save(batch);
    }

    private Map<String, Object> toObjectMap(Map<String, String> values) {

        Map<String, Object> result = new LinkedHashMap<>();
        result.putAll(values);

        return result;
    }

    private String calculateFileHash(MultipartFile file) throws IOException {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(file.getBytes());

            return HexFormat.of().formatHex(hash);

        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException(
                    "Unable to calculate file hash.", exception);
        }
    }
}