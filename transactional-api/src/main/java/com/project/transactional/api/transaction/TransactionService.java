package com.project.transactional.api.transaction;

public interface TransactionService {
    Transaction createTransaction(TransactionDto dto) throws Exception;
}
