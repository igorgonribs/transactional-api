package com.project.transactional.api.domain;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {

	private Integer accountId;
	private Integer operationTypeId;
	private BigDecimal amount;
}
