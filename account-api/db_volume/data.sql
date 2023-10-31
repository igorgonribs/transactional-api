INSERT INTO db_transactional.accounts (document_number) VALUES ("12345678900");

INSERT INTO db_transactional.operation_types (name, operation_signal)
    VALUES ("COMPRA A VISTA", "negative"),
           ("COMPRA PARCELADA", "negative"),
           ("SAQUE", "negative"),
           ("PAGAMENTO", "positive");