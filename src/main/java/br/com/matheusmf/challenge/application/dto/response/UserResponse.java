package br.com.matheusmf.challenge.application.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
	
	private String id;
	private String name;
	private String cpf;
	private String email;
	private LocalDate birthdate;
	private LocalDateTime createdAt;
	private LocalDateTime lastUpdatedAt;

}
