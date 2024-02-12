package br.com.matheusmf.challenge.core.service;

import br.com.matheusmf.challenge.core.domain.User;
import br.com.matheusmf.challenge.core.service.validation.ValidationResult;

public interface UserValidationService {
	
	ValidationResult validate(User user); 

}
