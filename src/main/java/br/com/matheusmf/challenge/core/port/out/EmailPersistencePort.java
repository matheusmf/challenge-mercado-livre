package br.com.matheusmf.challenge.core.port.out;

import br.com.matheusmf.challenge.core.domain.Email;

public interface EmailPersistencePort {
	
	Email saveEmail(Email email);

}
