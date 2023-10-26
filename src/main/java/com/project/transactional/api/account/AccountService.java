package com.project.transactional.api.service;

import com.project.transactional.api.domain.Account;
import com.project.transactional.api.dto.AccountDto;

import javax.ws.rs.InternalServerErrorException;
import java.util.Optional;

public interface AccountService {
    Account createAccount(AccountDto dto) throws InternalServerErrorException;
    Optional<Account> getAccount(Integer id);
}
