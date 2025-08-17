CREATE TABLE users
(
    id         UUID PRIMARY KEY,
    email      VARCHAR(255) UNIQUE,
    phone      VARCHAR(20) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE invoices
(
    id             UUID PRIMARY KEY,
    user_id        UUID REFERENCES users (id) ON DELETE CASCADE,
    invoice_number VARCHAR(100) NOT NULL,
    invoice_date   DATE         NOT NULL,
    due_date       DATE,
    notes          TEXT,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE invoice_items
(
    id          UUID PRIMARY KEY,
    invoice_id  UUID REFERENCES invoices (id) ON DELETE CASCADE,
    name        VARCHAR(255)   NOT NULL,
    quantity    INT            NOT NULL,
    unit_price  DECIMAL(10, 2) NOT NULL,
    total_price DECIMAL(10, 2) GENERATED ALWAYS AS (quantity * unit_price) STORED
);
