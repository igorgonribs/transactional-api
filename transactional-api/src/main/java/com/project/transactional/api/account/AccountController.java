package com.project.transactional.api.account;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {

	private AccountService service;

	@PostMapping
	public ResponseEntity<Account> createAccount(@Valid @RequestBody AccountDto dto)
			throws URISyntaxException {
		Account account = service.createAccount(dto);
		URI uri = new URI(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString() + "/" + account.getId());
		return ResponseEntity.created(uri).body(account);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Account> getAccountById(@PathVariable Integer id) {
		Optional<Account> response = service.getAccount(id);
		return response.map(account -> ResponseEntity.ok().body(account))
				.orElseGet(() -> ResponseEntity.noContent().build());
	}
}
