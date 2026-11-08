
-- ============================================
-- ReconcileX Enterprise
-- User and Role Management
-- ============================================

-- 1. Application users
CREATE TABLE app_users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    full_name VARCHAR(150) NOT NULL,

    email VARCHAR(255) NOT NULL,

    password_hash VARCHAR(255) NOT NULL,

    is_active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Ensure email addresses are unique regardless of case
CREATE UNIQUE INDEX uq_app_users_email_lower
ON app_users (LOWER(email));


-- 2. Application roles
CREATE TABLE roles (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    role_code VARCHAR(50) NOT NULL UNIQUE,

    role_name VARCHAR(100) NOT NULL,

    description VARCHAR(255),

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);


-- 3. User-role mapping
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,

    role_id BIGINT NOT NULL,

    assigned_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (user_id, role_id),

    CONSTRAINT fk_user_roles_user
        FOREIGN KEY (user_id)
        REFERENCES app_users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_user_roles_role
        FOREIGN KEY (role_id)
        REFERENCES roles(id)
        ON DELETE RESTRICT
);


-- Index for finding users assigned to a role
CREATE INDEX idx_user_roles_role_id
ON user_roles(role_id);


-- 4. Insert predefined application roles
INSERT INTO roles (role_code, role_name, description)
VALUES
(
    'ADMIN',
    'Administrator',
    'Manages users, roles and application configuration'
),
(
    'ANALYST',
    'Finance Analyst',
    'Uploads transaction files and performs reconciliation'
),
(
    'MANAGER',
    'Finance Manager',
    'Reviews reconciliation exceptions and resolutions'
),
(
    'AUDITOR',
    'Auditor',
    'Reviews reconciliation records and audit history'
);
