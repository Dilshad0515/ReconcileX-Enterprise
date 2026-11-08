
-- ============================================
-- ReconcileX Enterprise
-- Import Row Error Tracking
-- ============================================

CREATE TABLE import_row_errors (

    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    import_batch_id BIGINT NOT NULL,

    row_number INTEGER NOT NULL,

    raw_row_data JSONB,

    error_code VARCHAR(100) NOT NULL,

    error_message TEXT NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_import_row_errors_batch
        FOREIGN KEY (import_batch_id)
        REFERENCES import_batches(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_import_row_errors_row_number
        CHECK (row_number > 0),

    CONSTRAINT uq_import_row_errors_batch_row
        UNIQUE (import_batch_id, row_number)
);

CREATE INDEX idx_import_row_errors_batch_id
ON import_row_errors(import_batch_id);
