-- Create schema if not exist
CREATE SCHEMA IF NOT EXISTS account;

CREATE OR REPLACE FUNCTION update_updated_on_column()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_on = now();
    RETURN NEW;
END;
$$ language 'plpgsql';
-- Create table account if not exist
CREATE TABLE IF NOT EXISTS account.account
(
    id              SERIAL PRIMARY KEY,
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
    id             SERIAL PRIMARY KEY,
    date           timestamp                NOT NULL,
    type           VARCHAR(255)             NOT NULL,
    amount         DOUBLE PRECISION,
    balance        DOUBLE PRECISION,
    account_number BIGINT
        CONSTRAINT account_number_id_fk references account.account,
    created_on     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_on     TIMESTAMP WITH TIME ZONE
);
CREATE TRIGGER document_updated_on_trigger
    BEFORE UPDATE
    ON account.account
    FOR EACH ROW
EXECUTE PROCEDURE
    update_updated_on_column();

CREATE TRIGGER document_updated_on_trigger
    BEFORE UPDATE
    ON account.movements
    FOR EACH ROW
EXECUTE PROCEDURE
    update_updated_on_column();