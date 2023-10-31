package com.project.transactional.api.service;

import com.project.transactional.api.domain.Transaction;
import com.project.transactional.api.domain.TransactionDto;

public interface TransactionService {
    Transaction createTransaction(TransactionDto dto) throws Exception;
}
