package com.project.transactional.api.account;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    public Account(AccountDto dto) {
        this.documentNumber = dto.getDocumentNumber();
    }

    @Id
    private Integer id;
    private String documentNumber;
}
