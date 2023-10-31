package com.project.account.api.domain;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class AccountDto {
	@Getter
	@Setter
	@Size(min = 11, max = 11, message = "Field documentNumber must be 11 digits long.")
	@Digits(integer = 11, fraction = 0, message = "Field documentNumber must contain only digits.")
	private String documentNumber;
}
