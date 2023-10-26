package com.project.transactional.api.transaction;

import com.project.transactional.api.account.Account;
import com.project.transactional.api.account.AccountService;
import com.project.transactional.api.operationtype.OperationTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private static final Logger logger = Logger.getLogger("");

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
        if(!operationTypeService.isOperationAmountValid(dto.getAmount(), dto.getOperationTypeId())) {
            String errorMessage = String.format("Invalid amount for operation with id %d", dto.getOperationTypeId());
            logger.severe(errorMessage);
            throw new InvalidParameterException(errorMessage);
        }

    }

    private void validateAccount(TransactionDto dto) {
        Optional<Account> account = accountService.getAccount(dto.getAccountId());
        if(account.isEmpty()) {
            String errorMessage = String.format("Account with id %d was not found.", dto.getAccountId());
            logger.severe(errorMessage);
            throw new NoSuchElementException(errorMessage);
        }
    }
}
