package com.project.transactional.api.transaction;

import com.project.transactional.api.transaction.Transaction;
import com.project.transactional.api.transaction.TransactionDto;

public interface TransactionService {
    Transaction createTransaction(TransactionDto dto) throws Exception;
}
