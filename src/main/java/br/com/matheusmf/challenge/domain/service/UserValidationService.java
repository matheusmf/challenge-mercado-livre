package br.com.matheusmf.challenge.domain.service;

import br.com.matheusmf.challenge.domain.User;
import br.com.matheusmf.challenge.domain.service.validation.ValidationResult;

public interface UserValidationService {
	
	ValidationResult validate(User user); 

}
