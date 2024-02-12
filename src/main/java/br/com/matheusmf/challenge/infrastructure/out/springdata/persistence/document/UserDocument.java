package br.com.matheusmf.challenge.infrastructure.out.springdata.persistence.document;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class UserDocument {
	
	@Id
	private String id;
	
	private String name;
	
	private String cpf;
	
	private String email;
	
	private LocalDate birthdate;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime lastUpdatedAt;

}
