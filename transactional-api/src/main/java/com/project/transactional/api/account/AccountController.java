package com.project.transactional.api.account;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {

    private AccountService service;

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody AccountDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createAccount(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Integer id) {
        Optional<Account> response = service.getAccount(id);
        return response.map(account -> ResponseEntity.ok().body(account)).orElseGet(() -> ResponseEntity.noContent().build());
    }
}
