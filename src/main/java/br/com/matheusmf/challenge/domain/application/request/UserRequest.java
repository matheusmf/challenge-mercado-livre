package br.com.matheusmf.challenge.domain.application.request;

import java.time.LocalDate;

import br.com.matheusmf.challenge.domain.User;
import jakarta.validation.constraints.NotBlank;

public class UserRequest {

	@NotBlank
	private String name;
	
	@NotBlank
	private String cpf;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private LocalDate birthdate;

	public String getName() {
		return name;
	}

	public String getCpf() {
		return cpf;
	}

	public String getEmail() {
		return email;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}
	
	public User toUser() {
		return new User(
				new User.Builder()
				.name(this.name)
				.cpf(this.cpf)
				.email(this.email)
				.birthdate(this.birthdate));
	}

}
