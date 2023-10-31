package com.project.transactional.api.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verifyNoInteractions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.project.transactional.api.domain.OperationType;
import com.project.transactional.api.repository.OperationTypeRepository;
import com.project.transactional.api.service.impl.OperationTypeServiceImpl;

import jakarta.ws.rs.InternalServerErrorException;

@ExtendWith(MockitoExtension.class)
@ExtendWith(OutputCaptureExtension.class)
public class OperationTypeServiceImplTest {
	@Mock
	private OperationTypeRepository repository;

	@InjectMocks
	private OperationTypeServiceImpl service;

	@BeforeEach
	public void beforeAll() {
		ReflectionTestUtils.setField(service, "availableOperationTypes", new ArrayList<>());
	}

	@Test
	public void isOperationAmountValid_should_success_for_all_amount_values() throws Exception {
		List<OperationType> operationTypes = getOperationTypes();

		// Valid amount for purchase in cash
		assertTrue(service.isOperationAmountValid(BigDecimal.valueOf(Math.random() * (-1000)), operationTypes.get(0)));

		// Valid amount for purchase in installments
		assertTrue(service.isOperationAmountValid(BigDecimal.valueOf(Math.random() * (-1000)), operationTypes.get(1)));

		// Valid amount for withdraw
		assertTrue(service.isOperationAmountValid(BigDecimal.valueOf(Math.random() * (-1000)), operationTypes.get(2)));

		// Valid amount for payment
		assertTrue(service.isOperationAmountValid(BigDecimal.valueOf(Math.random() * (1000)), operationTypes.get(3)));
	}

	@Test
	public void isOperationAmountValid_should_fail_for_all_amount_values() throws Exception {
		List<OperationType> operationTypes = getOperationTypes();

		// Invalid amount for purchase in cash
		assertFalse(service.isOperationAmountValid(BigDecimal.valueOf(Math.random() * (1000)), operationTypes.get(0)));

		// Invalid amount for purchase in installments
		assertFalse(service.isOperationAmountValid(BigDecimal.valueOf(Math.random() * (1000)), operationTypes.get(1)));

		// Invalid amount for withdraw
		assertFalse(service.isOperationAmountValid(BigDecimal.valueOf(Math.random() * (1000)), operationTypes.get(2)));

		// Invalid amount for payment
		assertFalse(service.isOperationAmountValid(BigDecimal.valueOf(Math.random() * (-1000)), operationTypes.get(3)));
	}

	@Test
	public void isOperationAmountValid_should_success_with_no_cached_operation_type_list() throws Exception {
		assertTrue(service.isOperationAmountValid(BigDecimal.valueOf(Math.random() * (-1000)),
				new OperationType(1, "COMPRA A VISTA", "negative")));
		verifyNoInteractions(repository);
	}

	@Test
	public void isOperationAmountValid_should_fail_with_invalid_operationSignal() throws Exception {
		Executable executable = () -> service.isOperationAmountValid(BigDecimal.valueOf(Math.random() * (-1000)),
				new OperationType(1, "invalid", "invalid"));
		assertThrows(InternalServerErrorException.class, executable);
	}

	@Test
	public void getOperationType_should_success_with_no_cached_operation_type_list() throws Exception {
		Mockito.when(repository.findAll()).thenReturn(getOperationTypes());
		Optional<OperationType> operationType = service.getOperationType(1);
		assertTrue(operationType.isPresent());
		Mockito.verify(repository, Mockito.times(1)).findAll();
	}

	@Test
	public void getOperationType_should_success_for_invalid_operation_type() throws Exception {
		Mockito.when(repository.findAll()).thenReturn(getOperationTypes());
		Optional<OperationType> operationType = service.getOperationType(0);
		assertTrue(operationType.isEmpty());
		Mockito.verify(repository, Mockito.times(1)).findAll();
	}

	@Test
	public void getOperationType_should_success_for_cached_operation_type_list() throws Exception {
		ReflectionTestUtils.setField(service, "availableOperationTypes", getOperationTypes());
		Optional<OperationType> operationType = service.getOperationType(1);
		assertTrue(operationType.isPresent());
		Mockito.verifyNoInteractions(repository);
	}

	private static List<OperationType> getOperationTypes() {
		return new ArrayList<>() {
			private static final long serialVersionUID = 1L;
			{
				OperationType purchaseInCash = new OperationType(1, "COMPRA A VISTA", "negative");
				OperationType purchaseInInstallments = new OperationType(2, "COMPRA PARCELADA", "negative");
				OperationType withdraw = new OperationType(3, "SAQUE", "negative");
				OperationType payment = new OperationType(4, "PAYMENT", "positive");

				add(purchaseInCash);
				add(purchaseInInstallments);
				add(withdraw);
				add(payment);
			}
		};
	}
}
