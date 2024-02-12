package br.com.matheusmf.challenge.core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.matheusmf.challenge.core.domain.User;
import br.com.matheusmf.challenge.core.port.out.UserPersistencePort;
import br.com.matheusmf.challenge.core.service.validation.ValidationResult;

@ExtendWith(MockitoExtension.class)
public class UserValidationServiceTest {
	
	@Mock
	private UserPersistencePort userPersistencePort;
	
	@InjectMocks
	private DomainUserValidationService userValidationService;
	
	private User user;
	
	@BeforeEach
	public void setup() {
		user = User.builder()
				.name("Matheus")
				.cpf("28106132064")
				.email("matheus@mercadolivre.com")
				.birthdate(LocalDate.of(1989, 6, 3))
				.build();
	}
	
	@DisplayName("JUnit test for Given User Object when Validate then Return ValidationResult Object")
    @Test
    void testGivenUserObject_WhenValidate_thenReturnValidationResultObject() {
        
        // When / Act
        ValidationResult validationResult = userValidationService.validate(user);
        
        // Then / Assert
        assertNotNull(validationResult);
        assertEquals(false, validationResult.notValid());
        assertNull(validationResult.getErrorMsg());
    }
	
	@DisplayName("JUnit test for Given Age Under 18 when Validate then Return ValidationResult Object")
	@Test
	void testGivenAgeUnder18_WhenValidate_thenReturnValidationResultObject() {
		
		// Given / Arrange
		user.setBirthdate(LocalDate.now());
        
        // When / Act
        ValidationResult validationResult = userValidationService.validate(user);
        
        // Then / Assert
        assertNotNull(validationResult);
        assertEquals(true, validationResult.notValid());
        assertEquals("Only users over 18 years old are allowed", validationResult.getErrorMsg());
    }
	
	@DisplayName("JUnit test for Given Existing Cpf when Validate then Return ValidationResult Object")
	@Test
	void testGivenExistingCpf_WhenValidate_thenReturnValidationResultObject() {
		// Given / Arrange
		given(userPersistencePort.findByCpf(user.getCpf())).willReturn(Optional.of(new User()));

		// When / Act
		ValidationResult validationResult = userValidationService.validate(user);

		// Then / Assert
		assertNotNull(validationResult);
		assertEquals(true, validationResult.notValid());
		assertEquals(String.format("CPF [%s] already exists", user.getCpf()), validationResult.getErrorMsg());
	}
	
	@DisplayName("JUnit test for Given Id and Existing Cpf when Validate then Return ValidationResult Object")
	@Test
	void testGivenIdAndExistingCpf_WhenValidate_thenReturnValidationResultObject() {
		// Given / Arrange
		user.setId(UUID.randomUUID().toString());
		given(userPersistencePort.findByCpfAndIdNot(user.getCpf(), user.getId())).willReturn(Optional.of(new User()));

		// When / Act
		ValidationResult validationResult = userValidationService.validate(user);

		// Then / Assert
		assertNotNull(validationResult);
		assertEquals(true, validationResult.notValid());
		assertEquals(String.format("CPF [%s] already exists", user.getCpf()), validationResult.getErrorMsg());
	}
	
	@DisplayName("JUnit test for Given Existing Email when Validate then Return ValidationResult Object")
	@Test
	void testGivenExistingEmial_WhenValidate_thenReturnValidationResultObject() {
		// Given / Arrange
		given(userPersistencePort.findByEmail(user.getEmail())).willReturn(Optional.of(new User()));

		// When / Act
		ValidationResult validationResult = userValidationService.validate(user);

		// Then / Assert
		assertNotNull(validationResult);
		assertEquals(true, validationResult.notValid());
		assertEquals(String.format("Email [%s] is already taken", user.getEmail()), validationResult.getErrorMsg());
	}
	
	@DisplayName("JUnit test for Given Id and Existing Email when Validate then Return ValidationResult Object")
	@Test
	void testGivenIdAndExistingEmail_WhenValidate_thenReturnValidationResultObject() {
		// Given / Arrange
		user.setId(UUID.randomUUID().toString());
		given(userPersistencePort.findByEmailAndIdNot(user.getEmail(), user.getId())).willReturn(Optional.of(new User()));

		// When / Act
		ValidationResult validationResult = userValidationService.validate(user);

		// Then / Assert
		assertNotNull(validationResult);
		assertEquals(true, validationResult.notValid());
		assertEquals(String.format("Email [%s] is already taken", user.getEmail()), validationResult.getErrorMsg());
	}
	
	@DisplayName("JUnit test for Given Invalid Cpf when Validate then Return ValidationResult Object")
	@Test
	void testGivenInvalidCpf_WhenValidate_thenReturnValidationResultObject() {
		
		// Given / Arrange
		user.setCpf("00000000000");
        
        // When / Act
        ValidationResult validationResult = userValidationService.validate(user);
        
        // Then / Assert
        assertNotNull(validationResult);
        assertEquals(true, validationResult.notValid());
        assertEquals(String.format("CPF [%s] is invalid", user.getCpf()), validationResult.getErrorMsg());
    }

}
