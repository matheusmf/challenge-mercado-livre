package br.com.matheusmf.challenge.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.matheusmf.challenge.domain.DomainException;
import br.com.matheusmf.challenge.domain.User;
import br.com.matheusmf.challenge.domain.repository.UserRepository;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class DomainUserServiceTest {
	
	@Mock
	private UserRepository repository;
	
	@InjectMocks
	private DomainUserService service;
	
	private User user;
	
	@BeforeEach
	public void setup() {
		user = new User(
			new User.Builder()
				.name("Matheus")
				.cpf("28106132064")
				.email("matheus@mercadolivre.com")
				.birthdate(LocalDate.of(1989, 6, 3)));
	}
	
	@DisplayName("JUnit test for Given User Object when Create User then Return User Object")
    @Test
    void testGivenUserObject_WhenCreateUser_thenReturnUserObject() {
        
        // Given / Arrange
        given(repository.existsByCpf(anyString())).willReturn(false);
        given(repository.existsByEmail(anyString())).willReturn(false);
        given(repository.save(user)).willReturn(user);
        
        // When / Act
        User createdUser = service.createUser(user);
        
        // Then / Assert
        assertNotNull(createdUser);
        assertEquals("Matheus", createdUser.getName());
    }
	
	@DisplayName("JUnit test for Given Existing Cpf when Create User then throws Exception")
	@Test
	void testGivenExistingCpf_WhenCreateUser_thenThrowsException() {

		// Given / Arrange
		given(repository.existsByEmail(anyString())).willReturn(true);

		// When / Act
		assertThrows(DomainException.class, () -> {
			service.createUser(user);
		});

		// Then / Assert
		verify(repository, never()).save(any(User.class));
	}

	@DisplayName("JUnit test for Given Existing Email when Create User then throws Exception")
	@Test
	void testGivenExistingEmail_WhenCreateUser_thenThrowsException() {

		// Given / Arrange
		given(repository.existsByCpf(anyString())).willReturn(true);

		// When / Act
		assertThrows(DomainException.class, () -> {
			service.createUser(user);
		});

		// Then / Assert
		verify(repository, never()).save(any(User.class));
	}

}