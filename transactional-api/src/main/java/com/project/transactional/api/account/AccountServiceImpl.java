package com.project.transactional.api.account;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.project.transactional.api.transaction.TransactionServiceImpl;

import jakarta.ws.rs.InternalServerErrorException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
	private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

	private AccountRepository repository;

	@Override
	public Account createAccount(AccountDto dto) throws InternalServerErrorException {
		try {
			logger.info(String.format("Creating new account for document %s", dto.getDocumentNumber()));
			Account account = new Account(dto);
			return repository.save(account);
		} catch (Exception ex) {
			logger.error(String.format("Failed to create account for document %s", dto.getDocumentNumber()));
			throw new InternalServerErrorException(String.format("A problem occurred: %s", ex.getMessage()));
		}
	}

	@Override
	public Optional<Account> getAccount(Integer id) {
		logger.info(String.format("Getting account for id %d", id));
		return repository.findById(id);
	}
}
