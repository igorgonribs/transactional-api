package com.project.transactional.api.account;

import com.project.transactional.api.account.Account;
import com.project.transactional.api.account.AccountDto;
import com.project.transactional.api.account.AccountRepository;
import com.project.transactional.api.account.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.ws.rs.InternalServerErrorException;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private static final Logger logger = Logger.getLogger("");

    private AccountRepository repository;

    @Override
    public Account createAccount(AccountDto dto) throws InternalServerErrorException {
        try {
            logger.fine(String.format("Creating new account for document %s", dto.getDocumentNumber()));
            Account account = new Account(dto);
            return repository.save(account);
        } catch(Exception ex) {
            logger.severe(String.format("Failed to create account for document %s", dto.getDocumentNumber()));
            throw new InternalServerErrorException(String.format("A problem occurred: %s", ex.getMessage()));
        }
    }

    @Override
    public Optional<Account> getAccount(Integer id) {
        logger.fine(String.format("Getting account for id %d", id));
        return repository.findById(id);
    }
}
