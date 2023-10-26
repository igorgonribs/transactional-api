package com.project.transactional.api.operationtype;

import java.math.BigDecimal;

public interface OperationTypeService {
    Boolean isOperationAmountValid(BigDecimal amount, Integer operationTypeId) throws Exception;
}
