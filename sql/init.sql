-- Users Table
CREATE TABLE users
(
    id         BIGINT                                        NOT NULL AUTO_INCREMENT PRIMARY KEY, -- Unique user ID (Primary Key)
    email      VARCHAR(255)                                  NOT NULL UNIQUE,                     -- Email (must be unique)
    password   VARCHAR(255)                                  NOT NULL,                            -- Encrypted password
    first_name VARCHAR(255)                                  NOT NULL,                            -- First name
    last_name  VARCHAR(255)                                  NOT NULL,                            -- Last name
    role       ENUM ('CONTRACTOR', 'SUBCONTRACTOR', 'ADMIN') NOT NULL                             -- Role specification
);

-- Contractors Table
CREATE TABLE contractors
(
    user_id      BIGINT NOT NULL PRIMARY KEY,   -- Primary key, linked to `users`
    address      VARCHAR(255) DEFAULT NULL,     -- Contractor's address
    company_name VARCHAR(255) DEFAULT NULL,     -- Contact compan yname
    FOREIGN KEY (user_id) REFERENCES users (id) -- Link to `users`
);

-- Subcontractors Table
CREATE TABLE subcontractors
(
    user_id       BIGINT NOT NULL PRIMARY KEY,                   -- Primary key, linked to `users`
    contractor_id BIGINT NOT NULL,                               -- Links subcontractor to a contractor
    utr           VARCHAR(255) DEFAULT NULL UNIQUE,              -- Unique Taxpayer Reference (UTR)
    FOREIGN KEY (user_id) REFERENCES users (id),                 -- Link to `users`
    FOREIGN KEY (contractor_id) REFERENCES contractors (user_id) -- Link to `contractor`
);


CREATE table invoices
(

    id               BIGINT                             NOT NULL PRIMARY KEY,
    contractor_id    BIGINT                             NOT NULL,
    subcontractor_id BIGINT                             NOT NULL,
    job_name         VARCHAR(255)     DEFAULT NULL UNIQUE,
    data             TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    days             INT              DEFAULT 0,
    amount           DOUBLE PRECISION DEFAULT 0.0,
    status           VARCHAR(8)       DEFAULT 'PENDING' NOT NULL
);
