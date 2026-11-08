
-- ============================================
-- ReconcileX Enterprise
-- Link Import Batches to Application Users
-- ============================================

ALTER TABLE import_batches
ADD COLUMN IF NOT EXISTS uploaded_by_user_id BIGINT;

ALTER TABLE import_batches
ADD CONSTRAINT fk_import_batches_uploaded_by
FOREIGN KEY (uploaded_by_user_id)
REFERENCES app_users(id)
ON DELETE SET NULL;

CREATE INDEX idx_import_batches_uploaded_by
ON import_batches(uploaded_by_user_id);
