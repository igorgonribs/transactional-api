package com.project.transactional.api.transaction;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionController {

	private TransactionService service;

	@PostMapping
	public ResponseEntity<Transaction> createAccount(@RequestBody TransactionDto dto) throws Exception {
		Transaction transaction = service.createTransaction(dto);
		URI uri = new URI(
				ServletUriComponentsBuilder.fromCurrentContextPath().toUriString() + "/" + transaction.getId());
		return ResponseEntity.created(uri).body(transaction);
	}
}
