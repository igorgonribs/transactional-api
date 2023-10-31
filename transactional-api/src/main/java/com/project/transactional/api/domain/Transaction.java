package com.project.transactional.api.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

	public Transaction(TransactionDto dto) {
		this.accountId = dto.getAccountId();
		this.amount = dto.getAmount();
		this.operationTypeId = dto.getOperationTypeId();
		this.eventDate = LocalDateTime.now();
	}

	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Setter
	@Getter
	@NotNull
	private BigDecimal amount;
	@Setter
	@Getter
	@NotNull
	private LocalDateTime eventDate;
	@Setter
	@Getter
	private Integer operationTypeId;
	@Setter
	@Getter
	private Integer accountId;
}
