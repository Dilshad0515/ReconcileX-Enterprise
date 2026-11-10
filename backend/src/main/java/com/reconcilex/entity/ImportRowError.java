
package com.reconcilex.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "import_row_errors", uniqueConstraints = {
        @UniqueConstraint(name = "uq_import_row_errors_batch_row", columnNames = { "import_batch_id", "row_number" })
})
public class ImportRowError {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "import_batch_id", nullable = false)
    private ImportBatch importBatch;

    @Column(name = "row_number", nullable = false)
    private Integer rowNumber;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "raw_row_data", columnDefinition = "jsonb")
    private Map<String, Object> rawRowData;

    @Column(name = "error_code", nullable = false, length = 100)
    private String errorCode;

    @Column(name = "error_message", nullable = false, columnDefinition = "text")
    private String errorMessage;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    protected ImportRowError() {
    }

    public ImportRowError(
            ImportBatch importBatch,
            int rowNumber,
            String errorCode,
            String errorMessage) {
        this.importBatch = importBatch;
        this.rowNumber = rowNumber;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public Long getId() {
        return id;
    }

    public ImportBatch getImportBatch() {
        return importBatch;
    }

    public void setImportBatch(ImportBatch importBatch) {
        this.importBatch = importBatch;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public Map<String, Object> getRawRowData() {
        return rawRowData;
    }

    public void setRawRowData(Map<String, Object> rawRowData) {
        this.rawRowData = rawRowData;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
