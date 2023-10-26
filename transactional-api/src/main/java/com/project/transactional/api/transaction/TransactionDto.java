package com.project.transactional.api.transaction;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionDto {

    private Integer accountId;
    private Integer operationTypeId;
    private BigDecimal amount;
}
