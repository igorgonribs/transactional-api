package com.project.transactional.api.domain;

import com.project.transactional.api.dto.AccountDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
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
