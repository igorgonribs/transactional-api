package com.project.transactional.api.service.impl;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.project.transactional.api.domain.OperationType;
import com.project.transactional.api.domain.Transaction;
import com.project.transactional.api.domain.TransactionDto;
import com.project.transactional.api.exception.InvalidAmountException;
import com.project.transactional.api.repository.TransactionRepository;
import com.project.transactional.api.service.OperationTypeService;
import com.project.transactional.api.service.TransactionService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
	private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

	private TransactionRepository repository;
	private OperationTypeService operationTypeService;

	@Override
	public Transaction createTransaction(TransactionDto dto) throws Exception {
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
}
