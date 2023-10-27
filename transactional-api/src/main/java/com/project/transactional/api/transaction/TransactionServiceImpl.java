package com.project.transactional.api.transaction;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.project.transactional.api.account.Account;
import com.project.transactional.api.account.AccountService;
import com.project.transactional.api.exception.InvalidAmountException;
import com.project.transactional.api.operationtype.OperationType;
import com.project.transactional.api.operationtype.OperationTypeService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
	private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

	private TransactionRepository repository;
	private OperationTypeService operationTypeService;
	private AccountService accountService;

	@Override
	public Transaction createTransaction(TransactionDto dto) throws Exception {
		validateAccount(dto);
		validateOperationAmount(dto);

		Transaction transaction = new Transaction(dto);
		transaction = repository.save(transaction);

		logger.trace(String.format("Transaction successfully registered: %s", transaction.toString()));
		return transaction;
	}

	private void validateOperationAmount(TransactionDto dto) throws Exception {
		Optional<OperationType> operationType = operationTypeService.getOperationType(dto.getOperationTypeId());
		if (operationType.isEmpty()) {
			String errorMessage = String.format("Operation with id %d was not found", dto.getOperationTypeId());
			logger.error(errorMessage);
			throw new NoSuchElementException(errorMessage);
		}

		Boolean isAmountValidForOperationType = operationTypeService.isOperationAmountValid(dto.getAmount(),
				operationType.get());
		if (!isAmountValidForOperationType) {
			String errorMessage = String.format("Invalid amount for operation with id %d", dto.getOperationTypeId());
			logger.error(errorMessage);
			throw new InvalidAmountException(errorMessage);
		}

		logger.trace(String.format("Transaction for account %d with amount %f is valid for requested operation %s",
				dto.getAccountId(), dto.getAmount(), operationType.get().getName()));
	}

	private void validateAccount(TransactionDto dto) {
		Optional<Account> account = accountService.getAccount(dto.getAccountId());
		if (account.isEmpty()) {
			String errorMessage = String.format("Account with id %d was not found.", dto.getAccountId());
			logger.error(errorMessage);
			throw new NoSuchElementException(errorMessage);
		}
	}
}
