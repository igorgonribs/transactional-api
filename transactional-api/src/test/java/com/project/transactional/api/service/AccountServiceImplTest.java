package com.project.transactional.api.service;

import com.project.transactional.api.account.Account;
import com.project.transactional.api.account.AccountDto;
import com.project.transactional.api.account.AccountRepository;
import com.project.transactional.api.account.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.OutputCaptureExtension;

import javax.ws.rs.InternalServerErrorException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@ExtendWith(OutputCaptureExtension.class)
public class AccountServiceImplTest {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void getAccount_should_success_with_existing_id() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(new Account()));
        Optional<Account> response = accountService.getAccount(1);
        assertTrue(response.isPresent());
    }

    @Test
    void getAccount_should_success_with_non_existing_id() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Optional<Account> response = accountService.getAccount(0);
        assertTrue(response.isEmpty());
    }

    @Test
    void createAccount_should_success_with_valid_dto() {
        AccountDto dto = new AccountDto();
        dto.setDocumentNumber("12345678900");
        Account expected = new Account(dto);
        Mockito.when(repository.save(Mockito.any(Account.class))).thenReturn(expected);

        Account response = accountService.createAccount(dto);
        assertEquals(expected.getDocumentNumber(), response.getDocumentNumber());
        assertEquals(expected.getDocumentNumber(), dto.getDocumentNumber());
    }

    @Test
    void createAccount_should_fail_with_invalid_dto() {
        AccountDto dto = new AccountDto();
        Mockito.when(repository.save(Mockito.any(Account.class))).thenThrow(new RuntimeException());

        Executable executable = () -> accountService.createAccount(dto);

        assertThrows(InternalServerErrorException.class, executable);
    }
}
