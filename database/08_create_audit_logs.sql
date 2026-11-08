
-- ============================================
-- ReconcileX Enterprise
-- Audit Logging
-- ============================================

CREATE TABLE audit_logs (

    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    user_id BIGINT,

    action VARCHAR(100) NOT NULL,

    entity_type VARCHAR(100) NOT NULL,

    entity_id BIGINT,

    description TEXT,

    metadata JSONB,

    ip_address VARCHAR(45),

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_audit_logs_user
        FOREIGN KEY (user_id)
        REFERENCES app_users(id)
        ON DELETE SET NULL
);

CREATE INDEX idx_audit_logs_user_id
ON audit_logs(user_id);

CREATE INDEX idx_audit_logs_entity
ON audit_logs(entity_type, entity_id);

CREATE INDEX idx_audit_logs_created_at
ON audit_logs(created_at);
