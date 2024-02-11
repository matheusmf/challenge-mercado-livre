package br.com.matheusmf.challenge.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class User {
	private UUID id;
	private String name;
	private String cpf;
	private String email;
	private LocalDate birthdate;
	private LocalDateTime createdAt;
	private LocalDateTime lastUpdatedAt;
	
	public User(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.cpf = builder.cpf;
		this.email = builder.email;
		this.birthdate = builder.birthdate;
		this.createdAt = builder.createdAt;
		this.lastUpdatedAt = builder.lastUpdatedAt;
	}
	
	public void assignCreateData() {
		this.id = UUID.randomUUID();
		this.createdAt = LocalDateTime.now();
	}
	
	public void assignUpdateData() {
		this.lastUpdatedAt = LocalDateTime.now();
	}

	public UUID getId() {
		return id;
	}

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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getLastUpdatedAt() {
		return lastUpdatedAt;
	}

	public static class Builder {
		private UUID id;
		private String name;
		private String cpf;
		private String email;
		private LocalDate birthdate;
		private LocalDateTime createdAt;
		private LocalDateTime lastUpdatedAt;
		
		public Builder id(UUID id) {
			this.id = id;
			return this;
		}
		
		public Builder name(String name) {
			this.name = name;
			return this;
		}
		
		public Builder cpf(String cpf) {
			this.cpf = cpf;
			return this;
		}
		
		public Builder email(String email) {
			this.email = email;
			return this;
		}
		
		public Builder birthdate(LocalDate birthdate) {
			this.birthdate = birthdate;
			return this;
		}
		
		public Builder createdAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
			return this;
		}
		
		public Builder lastUpdatedAt(LocalDateTime lastUpdatedAt) {
			this.lastUpdatedAt = lastUpdatedAt;
			return this;
		}
		
	}

}
