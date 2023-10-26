package com.project.transactional.api.controller;

import com.project.transactional.api.domain.Transaction;
import com.project.transactional.api.dto.TransactionDto;
import com.project.transactional.api.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionController {

    private TransactionService service;

    @PostMapping
    public ResponseEntity<Transaction> createAccount(@RequestBody TransactionDto dto) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createTransaction(dto));
    }
}
