CREATE SCHEMA IF NOT EXISTS transactional;

DROP TABLE IF EXISTS transactional.accounts;
DROP TABLE IF EXISTS transactional.operation_types;
DROP TABLE IF EXISTS transactional.transactions;

CREATE TABLE transactional.accounts(
    id integer primary key,
    document_number VARCHAR(11) not null
);

CREATE TABLE transactional.operation_types(
    id integer primary key,
    name varchar(50),
    "signal" varchar(50)
);

CREATE TABLE transactional.transactions(
    id integer primary key,
    account_id integer,
    operation_type_id integer,
    amount DECIMAL(19,2),
    event_date DATETIME
);

alter table transactional.transactions add constraint fk_transaction_account_id foreign key (account_id) references transactional.accounts (id);
alter table transactional.transactions add constraint fk_transaction_order_type_id foreign key (operation_type_id) references transactional.operation_types (id);

INSERT INTO transactional.accounts (document_number) VALUES ("12345678900");

INSERT INTO transactional.operation_types (name, "signal")
    VALUES ("COMPRA A VISTA", "negative"),
           ("COMPRA PARCELADA", "negative"),
           ("SAQUE", "negative"),
           ("PAGAMENTO", "positive");