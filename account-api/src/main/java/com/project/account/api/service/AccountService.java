package com.project.account.api.service;

import java.util.Optional;

import javax.ws.rs.InternalServerErrorException;

import com.project.account.api.domain.Account;
import com.project.account.api.domain.AccountDto;

public interface AccountService {
    Account createAccount(AccountDto dto) throws InternalServerErrorException;
    Optional<Account> getAccount(Integer id);
}
