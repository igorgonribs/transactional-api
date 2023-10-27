package com.project.transactional.api.account;

import java.util.Optional;

import javax.ws.rs.InternalServerErrorException;

public interface AccountService {
    Account createAccount(AccountDto dto) throws InternalServerErrorException;
    Optional<Account> getAccount(Integer id);
}
