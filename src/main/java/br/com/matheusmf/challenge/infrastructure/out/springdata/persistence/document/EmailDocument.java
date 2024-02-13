package br.com.matheusmf.challenge.infrastructure.out.springdata.persistence.document;

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
@Document(collection = "email")
public class EmailDocument {
	
	@Id
	private String id;
	
	private String to;
	
	private String welcomeMessage;

}
