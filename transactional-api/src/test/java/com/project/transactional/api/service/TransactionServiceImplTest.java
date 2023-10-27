package com.project.transactional.api.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.OutputCaptureExtension;

import com.project.transactional.api.account.Account;
import com.project.transactional.api.account.AccountService;
import com.project.transactional.api.exception.InvalidAmountException;
import com.project.transactional.api.operationtype.OperationType;
import com.project.transactional.api.operationtype.OperationTypeService;
import com.project.transactional.api.transaction.Transaction;
import com.project.transactional.api.transaction.TransactionDto;
import com.project.transactional.api.transaction.TransactionRepository;
import com.project.transactional.api.transaction.TransactionServiceImpl;

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
		TransactionDto dto = new TransactionDto();
		dto.setAccountId(1);
		dto.setAmount(BigDecimal.ONE);
		dto.setOperationTypeId(1);

		Transaction expected = new Transaction(dto);
		expected.setAccountId(1);
		expected.setAmount(BigDecimal.ONE);
		expected.setOperationTypeId(1);
		expected.setEventDate(LocalDateTime.now());

		OperationType mockedOperationType = new OperationType(1, "Mock", "Mock");

		Mockito.when(operationTypeService.isOperationAmountValid(any(BigDecimal.class), any(OperationType.class)))
				.thenReturn(true);
		Mockito.when(accountService.getAccount(anyInt())).thenReturn(Optional.of(new Account()));
		Mockito.when(operationTypeService.getOperationType(anyInt())).thenReturn(Optional.of(mockedOperationType));
		Mockito.when(repository.save(any(Transaction.class))).thenReturn(expected);

		Transaction actual = service.createTransaction(dto);

		Assertions.assertEquals(expected.getAccountId(), actual.getAccountId());
		Assertions.assertEquals(expected.getAmount(), actual.getAmount());
		Assertions.assertEquals(expected.getOperationTypeId(), actual.getOperationTypeId());
		Assertions.assertNotNull(actual.getEventDate());
	}

	@Test
	public void createTransaction_should_fail_with_invalid_amount_for_operation() throws Exception {
		TransactionDto dto = new TransactionDto();
		dto.setAccountId(1);
		dto.setAmount(BigDecimal.ONE);
		dto.setOperationTypeId(1);
		OperationType mockedOperationType = new OperationType(1, "Mock", "Mock");

		Mockito.when(operationTypeService.getOperationType(anyInt())).thenReturn(Optional.of(mockedOperationType));
		Mockito.when(operationTypeService.isOperationAmountValid(any(BigDecimal.class), any(OperationType.class)))
				.thenReturn(false);
		Mockito.when(accountService.getAccount(anyInt())).thenReturn(Optional.of(new Account()));

		Executable executable = () -> service.createTransaction(dto);

		Assertions.assertThrows(InvalidAmountException.class, executable);
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

		Mockito.when(operationTypeService.getOperationType(anyInt())).thenReturn(Optional.empty());
		Mockito.when(accountService.getAccount(anyInt())).thenReturn(Optional.of(new Account()));

		Executable executable = () -> service.createTransaction(dto);

		Assertions.assertThrows(NoSuchElementException.class, executable);
	}
}
