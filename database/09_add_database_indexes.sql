-- ReconcileX Enterprise
-- Additional database indexes

CREATE INDEX IF NOT EXISTS idx_import_batches_data_source
ON import_batches(data_source_id);

CREATE INDEX IF NOT EXISTS idx_source_transactions_batch
ON source_transactions(import_batch_id);

CREATE INDEX IF NOT EXISTS idx_source_transactions_reference
ON source_transactions(source_transaction_ref);

CREATE INDEX IF NOT EXISTS idx_source_transactions_occurred_at
ON source_transactions(occurred_at);

CREATE INDEX IF NOT EXISTS idx_source_transactions_type_status
ON source_transactions(transaction_type, transaction_status);