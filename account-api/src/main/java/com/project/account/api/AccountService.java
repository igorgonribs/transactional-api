package com.project.transactional.api.account;

import com.project.transactional.api.account.Account;
import com.project.transactional.api.account.AccountDto;

import javax.ws.rs.InternalServerErrorException;
import java.util.Optional;

public interface AccountService {
    Account createAccount(AccountDto dto) throws InternalServerErrorException;
    Optional<Account> getAccount(Integer id);
}
