package br.com.matheusmf.challenge.core.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private String id;
	private String name;
	private String cpf;
	private String email;
	private LocalDate birthdate;
	private LocalDateTime createdAt;
	private LocalDateTime lastUpdatedAt;
}
