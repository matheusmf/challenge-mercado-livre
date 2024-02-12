package br.com.matheusmf.challenge.application.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

	@NotBlank
	private String name;
	
	@NotBlank
	private String cpf;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private LocalDate birthdate;

}
