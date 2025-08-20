CREATE TABLE users
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email      VARCHAR(255) UNIQUE,
    phone      VARCHAR(20) UNIQUE,
    name       VARCHAR(128),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT check_email_or_phone CHECK (email IS NOT NULL OR phone IS NOT NULL)
);

CREATE TABLE invoices
(
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id        UUID REFERENCES users (id) ON DELETE CASCADE,
    invoice_number VARCHAR(100) NOT NULL,
    invoice_date   DATE         NOT NULL,
    due_date       DATE,
    notes          TEXT,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE invoice_items
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    invoice_id  UUID REFERENCES invoices (id) ON DELETE CASCADE,
    name        VARCHAR(255)   NOT NULL,
    quantity    INT            NOT NULL,
    unit_price  DECIMAL(10, 2) NOT NULL,
    total_price DECIMAL(10, 2) GENERATED ALWAYS AS (quantity * unit_price) STORED,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
