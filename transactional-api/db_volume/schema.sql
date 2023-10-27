CREATE SCHEMA IF NOT EXISTS db_transactional;

DROP TABLE IF EXISTS db_transactional.accounts;
DROP TABLE IF EXISTS db_transactional.operation_types;
DROP TABLE IF EXISTS db_transactional.transactions;

CREATE TABLE db_transactional.accounts(
    id int NOT NULL AUTO_INCREMENT,
    document_number VARCHAR(11) not null unique,
    PRIMARY KEY (id)
);

CREATE TABLE db_transactional.operation_types(
    id int NOT NULL AUTO_INCREMENT,
    name varchar(50) not null unique,
    operation_signal varchar(50) not null,
    PRIMARY KEY (id)
);

CREATE TABLE db_transactional.transactions(
    id int NOT NULL AUTO_INCREMENT,
    account_id integer,
    operation_type_id integer,
    amount DECIMAL(19,2) not null,
    event_date DATETIME not null,
    PRIMARY KEY (id)
);

alter table db_transactional.transactions add constraint fk_transaction_account_id foreign key (account_id) references db_transactional.accounts (id);
alter table db_transactional.transactions add constraint fk_transaction_order_type_id foreign key (operation_type_id) references db_transactional.operation_types (id);