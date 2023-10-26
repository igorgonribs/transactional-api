package com.project.transactional.api.service;

import com.project.transactional.api.domain.Account;
import com.project.transactional.api.domain.Transaction;
import com.project.transactional.api.dto.TransactionDto;
import com.project.transactional.api.repository.AccountRepository;
import com.project.transactional.api.repository.TransactionRepository;
import com.project.transactional.api.service.impl.AccountServiceImpl;
import com.project.transactional.api.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
@ExtendWith(OutputCaptureExtension.class)
public class TransactionServiceImplTest {
    @Mock
    private TransactionRepository repository;
    @Mock
    private OperationTypeService operationTypeService;
    @Mock
    private AccountService accountService;
    @InjectMocks
    private TransactionServiceImpl service;

    @Test
    public void createTransaction_should_success() throws Exception {
        Mockito.when(operationTypeService.isOperationAmountValid(any(BigDecimal.class), anyInt())).thenReturn(true);
        Mockito.when(accountService.getAccount(anyInt())).thenReturn(Optional.of(new Account()));

        TransactionDto dto = new TransactionDto();
        dto.setAccountId(1);
        dto.setAmount(BigDecimal.ONE);
        dto.setOperationTypeId(1);

        Transaction expected = new Transaction(dto);
        expected.setAccountId(1);
        expected.setAmount(BigDecimal.ONE);
        expected.setOperationTypeId(1);
        expected.setEventDate(LocalDateTime.now());
        Mockito.when(repository.save(any(Transaction.class))).thenReturn(expected);

        Transaction actual = service.createTransaction(dto);

        Assertions.assertEquals(expected.getAccountId(), actual.getAccountId());
        Assertions.assertEquals(expected.getAmount(), actual.getAmount());
        Assertions.assertEquals(expected.getOperationTypeId(), actual.getOperationTypeId());
        Assertions.assertNotNull(actual.getEventDate());
    }

    @Test
    public void createTransaction_should_fail_with_invalid_account() {
        TransactionDto dto = new TransactionDto();
        dto.setAccountId(1);
        dto.setAmount(BigDecimal.ONE);
        dto.setOperationTypeId(1);

        Mockito.when(accountService.getAccount(anyInt())).thenReturn(Optional.empty());

        Executable executable = () -> service.createTransaction(dto);

        Assertions.assertThrows(NoSuchElementException.class, executable);
    }

    @Test
    public void createTransaction_should_fail_with_invalid_operationType() throws Exception {
        TransactionDto dto = new TransactionDto();
        dto.setAccountId(1);
        dto.setAmount(BigDecimal.ONE);
        dto.setOperationTypeId(1);

        Mockito.when(operationTypeService.isOperationAmountValid(any(BigDecimal.class), anyInt())).thenReturn(false);
        Mockito.when(accountService.getAccount(anyInt())).thenReturn(Optional.of(new Account()));

        Executable executable = () -> service.createTransaction(dto);

        Assertions.assertThrows(InvalidParameterException.class, executable);
    }
}
