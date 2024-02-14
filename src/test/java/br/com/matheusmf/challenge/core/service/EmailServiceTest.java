package br.com.matheusmf.challenge.core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.matheusmf.challenge.core.domain.Email;
import br.com.matheusmf.challenge.core.domain.User;

@SpringBootTest
public class EmailServiceTest {
	
	@Autowired
	private EmailService emailService;
	
	@DisplayName("JUnit test for Given Email Object when Save Email then Return Email Object")
    @Test
    void testGivenEmailObject_WhenSaveEmail_thenReturnEmailObject() {
        
		// Given / Arrange
		User user = User.builder()
				.name("Matheus")
				.cpf("28106132064")
				.email("matheus@mercadolivre.com")
				.birthdate(LocalDate.of(1989, 6, 3))
				.build();
        
        // When / Act
        Email savedEmail = emailService.saveEmail(user);
        
        // Then / Assert
        assertNotNull(savedEmail);
        assertNotNull(savedEmail.getId());
        assertEquals(user.getEmail(), savedEmail.getTo());
        assertTrue(savedEmail.getWelcomeMessage().contains(user.getName()));
    }

}
