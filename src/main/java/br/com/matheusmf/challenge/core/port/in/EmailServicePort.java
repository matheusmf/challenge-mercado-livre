package br.com.matheusmf.challenge.core.port.in;

import br.com.matheusmf.challenge.core.domain.Email;
import br.com.matheusmf.challenge.core.domain.User;

public interface EmailServicePort {
	
	Email saveEmail(User user);

}
