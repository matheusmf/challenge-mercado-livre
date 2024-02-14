package br.com.matheusmf.challenge.infrastructure.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import br.com.matheusmf.challenge.core.domain.Email;
import br.com.matheusmf.challenge.infrastructure.out.springdata.adapter.EmailSpringDataAdapter;
import br.com.matheusmf.challenge.infrastructure.out.springdata.mapper.EmailDocumentMapper;
import br.com.matheusmf.challenge.infrastructure.out.springdata.persistence.document.EmailDocument;

@SpringJUnitConfig
@SpringBootTest
public class EmailSpringDataAdapterTest {
	
    @Autowired
    private EmailSpringDataAdapter emailSpringDataAdapter;
    
    @DisplayName("JUnit test for Given Email Object when Save then Return Saved Email")
    @Test
    void testGivenEmailObject_whenSave_thenReturnSavedEmail() {
    	
    	 // Given / Arrange
    	EmailDocument emailDocument = EmailDocument.builder()
						.id(UUID.randomUUID().toString())
						.to("matheus@mercadolivre.com")
						.welcomeMessage("Bem-vindo Matheus!")
						.build();
        
        // When / Act
        Email savedEmail = emailSpringDataAdapter.saveEmail(new EmailDocumentMapper().toDomain(emailDocument));
        
        // Then / Assert
        assertNotNull(savedEmail);
        assertNotNull(savedEmail.getId());
        assertEquals(emailDocument.getTo(), savedEmail.getTo());
        assertEquals(emailDocument.getWelcomeMessage(), savedEmail.getWelcomeMessage());
    }

}
