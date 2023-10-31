package com.project.transactional.api.service;

import java.math.BigDecimal;
import java.util.Optional;

import com.project.transactional.api.domain.OperationType;

public interface OperationTypeService {
	Boolean isOperationAmountValid(BigDecimal amount, OperationType operationType) throws Exception;
    Optional<OperationType> getOperationType(Integer id);
}
