package com.project.transactional.api.operationtype;

import java.math.BigDecimal;
import java.util.Optional;

public interface OperationTypeService {
	Boolean isOperationAmountValid(BigDecimal amount, OperationType operationType) throws Exception;
    Optional<OperationType> getOperationType(Integer id);
}
