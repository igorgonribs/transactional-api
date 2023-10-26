package com.project.transactional.api.service.impl;

import com.project.transactional.api.domain.Account;
import com.project.transactional.api.domain.Transaction;
import com.project.transactional.api.dto.TransactionDto;
import com.project.transactional.api.repository.TransactionRepository;
import com.project.transactional.api.service.AccountService;
import com.project.transactional.api.service.OperationTypeService;
import com.project.transactional.api.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository repository;
    private OperationTypeService operationTypeService;
    private AccountService accountService;
    @Override
    public Transaction createTransaction(TransactionDto dto) throws Exception {
        validateAccount(dto);
        validateOperationAmount(dto);

        Transaction transaction = new Transaction(dto);
        return repository.save(transaction);
    }

    private void validateOperationAmount(TransactionDto dto) throws Exception {
        if(!operationTypeService.isOperationAmountValid(dto.getAmount(), dto.getOperationTypeId()))
            throw new InvalidParameterException(String.format("Invalid amount for operation with id %d", dto.getOperationTypeId()));
    }

    private void validateAccount(TransactionDto dto) {
        Optional<Account> account = accountService.getAccount(dto.getAccountId());
        if(account.isEmpty())
            throw new NoSuchElementException(String.format("Account with id %d was not found.", dto.getAccountId()));
    }
}
