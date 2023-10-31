CREATE SCHEMA IF NOT EXISTS db_transactional;

DROP TABLE IF EXISTS db_transactional.accounts;
DROP TABLE IF EXISTS db_transactional.operation_types;
DROP TABLE IF EXISTS db_transactional.transactions;

CREATE TABLE db_transactional.accounts(
    id INT NOT NULL AUTO_INCREMENT,
    document_number VARCHAR(11) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE(document_number)
);

CREATE TABLE db_transactional.operation_types(
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    operation_signal VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE(name)
);

CREATE TABLE db_transactional.transactions(
    id INT NOT NULL AUTO_INCREMENT,
    account_id INTEGER,
    operation_type_id INTEGER,
    amount DECIMAL(19,2) NOT NULL,
    event_date DATETIME NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE db_transactional.transactions ADD CONSTRAINT fk_transaction_account_id FOREIGN KEY (account_id) REFERENCES db_transactional.accounts (id);
ALTER TABLE db_transactional.transactions ADD CONSTRAINT fk_transaction_order_type_id FOREIGN KEY (operation_type_id) REFERENCES db_transactional.operation_types (id);