package com.project.transactional.api.service.impl;

import com.project.transactional.api.domain.Account;
import com.project.transactional.api.dto.AccountDto;
import com.project.transactional.api.repository.AccountRepository;
import com.project.transactional.api.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.ws.rs.InternalServerErrorException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepository repository;

    @Override
    public Account createAccount(AccountDto dto) throws InternalServerErrorException {
        try {
            Account account = new Account(dto);
            return repository.save(account);
        } catch(Exception ex) {
            throw new InternalServerErrorException(String.format("A problem occurred: %s", ex.getMessage()));
        }
    }

    @Override
    public Optional<Account> getAccount(Integer id) {
        return repository.findById(id);
    }
}
