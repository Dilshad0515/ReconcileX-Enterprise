
package com.reconcilex.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "import_batches")
public class ImportBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "data_source_id", nullable = false)
    private DataSource dataSource;

    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    @Column(name = "file_hash", length = 64)
    private String fileHash;

    @Column(name = "batch_status", nullable = false, length = 30)
    private String batchStatus = "PENDING";

    @Column(name = "total_rows", nullable = false)
    private Integer totalRows = 0;

    @Column(name = "successful_rows", nullable = false)
    private Integer successfulRows = 0;

    @Column(name = "failed_rows", nullable = false)
    private Integer failedRows = 0;

    @Column(name = "imported_at", nullable = false, insertable = false, updatable = false)
    private OffsetDateTime importedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by_user_id")
    private AppUser uploadedBy;

    protected ImportBatch() {
    }

    public ImportBatch(DataSource dataSource, String fileName, String fileHash) {
        this.dataSource = dataSource;
        this.fileName = fileName;
        this.fileHash = fileHash;
        this.batchStatus = "PENDING";
        this.totalRows = 0;
        this.successfulRows = 0;
        this.failedRows = 0;
    }

    public Long getId() {
        return id;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public String getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public Integer getSuccessfulRows() {
        return successfulRows;
    }

    public void setSuccessfulRows(Integer successfulRows) {
        this.successfulRows = successfulRows;
    }

    public Integer getFailedRows() {
        return failedRows;
    }

    public void setFailedRows(Integer failedRows) {
        this.failedRows = failedRows;
    }

    public OffsetDateTime getImportedAt() {
        return importedAt;
    }

    public AppUser getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(AppUser uploadedBy) {
        this.uploadedBy = uploadedBy;
    }
}
