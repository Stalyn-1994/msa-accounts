-- Create schema if not exist
CREATE SCHEMA IF NOT EXISTS account;

-- Create table account if not exist
CREATE TABLE IF NOT EXISTS account.account
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer        VARCHAR(255)             NOT NULL,
    account_number  VARCHAR(255)             NOT NULL,
    type            VARCHAR(255)             NOT NULL,
    initial_balance DOUBLE PRECISION,
    status          BOOLEAN                           DEFAULT FALSE,
    created_on      TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_on      TIMESTAMP WITH TIME ZONE
);

-- Create table movements if not exist
CREATE TABLE IF NOT EXISTS account.movements
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    date           DATE                     NOT NULL,
    type           VARCHAR(255)             NOT NULL,
    amount         DOUBLE PRECISION,
    balance        DOUBLE PRECISION,
    account_number BIGINT
        CONSTRAINT account_number_id_fk references account.account,
    created_on     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_on     TIMESTAMP WITH TIME ZONE
);
