package com.project.transactional.api.transaction;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
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
    private Integer id;
    private BigDecimal amount;
    private LocalDateTime eventDate;
    private Integer operationTypeId;
    private Integer accountId;
}
