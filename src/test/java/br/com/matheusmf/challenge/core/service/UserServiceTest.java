package br.com.matheusmf.challenge.core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.matheusmf.challenge.core.domain.DomainException;
import br.com.matheusmf.challenge.core.domain.User;
import br.com.matheusmf.challenge.core.port.out.UserPersistencePort;
import br.com.matheusmf.challenge.core.service.validation.ValidationResult;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@Mock
	private UserPersistencePort userPersistencePort;
	
	@Mock
	private UserValidationService userValidationService;
	
	@InjectMocks
	private UserService service;
	
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
	
	@DisplayName("JUnit test for Given User Object when Create User then Return User Object")
    @Test
    void testGivenUserObject_WhenCreateUser_thenReturnUserObject() {
        
        // Given / Arrange
		given(userValidationService.validate(user)).willReturn(new ValidationResult(true, null));
        given(userPersistencePort.save(user)).willReturn(user);
        
        // When / Act
        User createdUser = service.createUser(user);
        
        // Then / Assert
        assertNotNull(createdUser);
        assertEquals("Matheus", createdUser.getName());
        assertNotNull(createdUser.getId());
        assertNotNull(createdUser.getCreatedAt());
    }
	
	@DisplayName("JUnit test for Given Existing Cpf when Create User then throws Exception")
	@Test
	void testGivenExistingCpf_WhenCreateUser_thenThrowsException() {

		// Given / Arrange
		given(userValidationService.validate(user)).willReturn(new ValidationResult(false, "errorMsg"));

		// When / Act
		assertThrows(DomainException.class, () -> {
			service.createUser(user);
		});

		// Then / Assert
		verify(userPersistencePort, never()).save(any(User.class));
	}

	@DisplayName("JUnit test for Given Existing Email when Create User then throws Exception")
	@Test
	void testGivenExistingEmail_WhenCreateUser_thenThrowsException() {

		// Given / Arrange
		given(userValidationService.validate(user)).willReturn(new ValidationResult(false, "errorMsg"));

		// When / Act
		assertThrows(DomainException.class, () -> {
			service.createUser(user);
		});

		// Then / Assert
		verify(userPersistencePort, never()).save(any(User.class));
	}
	
	@DisplayName("JUnit test for Given Age Under 18 when Create User then throws Exception")
	@Test
	void testGivenAgeUnder18_WhenCreateUser_thenThrowsException() {

		// Given / Arrange
		given(userValidationService.validate(user)).willReturn(new ValidationResult(false, "errorMsg"));
		user.setBirthdate(LocalDate.now());

		// When / Act
		assertThrows(DomainException.class, () -> {
			service.createUser(user);
		});

		// Then / Assert
		verify(userPersistencePort, never()).save(any(User.class));
	}
	
	@DisplayName("JUnit test for Given Existing Cpf when Update User then throws Exception")
	@Test
	void testGivenExistingCpf_WhenUpdateUser_thenThrowsException() {

		// Given / Arrange
		given(userPersistencePort.findById(anyString())).willReturn(Optional.of(user));
		given(userValidationService.validate(user)).willReturn(new ValidationResult(false, "errorMsg"));

		// When / Act
		assertThrows(DomainException.class, () -> {
			service.updateUser(UUID.randomUUID().toString(), user);
		});

		// Then / Assert
		verify(userPersistencePort, never()).save(any(User.class));
	}
	
	@DisplayName("JUnit test for Given Invalid Cpf when Create User then throws Exception")
	@Test
	void testGivenInvalidCpf_WhenCreateUser_thenThrowsException() {
		
		User cpfInvalidUser = User.builder()
					.name("Marcos")
					.cpf("00000000000")
					.email("marcos@mercadolivre.com")
					.birthdate(LocalDate.of(1989, 6, 3))
					.build();

		// Given / Arrange
		given(userValidationService.validate(cpfInvalidUser)).willReturn(new ValidationResult(false, "errorMsg"));

		// When / Act
		assertThrows(DomainException.class, () -> {
			service.createUser(cpfInvalidUser);
		});

		// Then / Assert
		verify(userPersistencePort, never()).save(any(User.class));
	}
	
	@DisplayName("JUnit test for Given User Object when Update User then Return User Object")
    @Test
    void testGivenUserObject_WhenUpdateUser_thenReturnUserObject() {
		
		String id = UUID.randomUUID().toString();
		User existingUser = User.builder()
					.name("Leandro")
					.cpf("54447091046")
					.email("leandro@mercadolivre.com")
					.birthdate(LocalDate.of(1998, 7, 1))
					.createdAt(LocalDateTime.now()).build();
        
        // Given / Arrange
		given(userPersistencePort.findById(id)).willReturn(Optional.of(existingUser));
		given(userValidationService.validate(existingUser)).willReturn(new ValidationResult(true, null));
        given(userPersistencePort.save(existingUser)).willReturn(existingUser);
        
        existingUser.setName("Leonardo");
        // When / Act
        User updatedUser = service.updateUser(id, existingUser);
        
        // Then / Assert
        assertNotNull(updatedUser);
        assertEquals("Leonardo", updatedUser.getName());
        assertNotNull(updatedUser.getLastUpdatedAt());
        assertTrue(updatedUser.getLastUpdatedAt().isAfter(existingUser.getCreatedAt()));
    }
	
	@DisplayName("JUnit test for Given Existing Email when Update User then throws Exception")
	@Test
	void testGivenExistingEmail_WhenUpdateUser_thenThrowsException() {

		// Given / Arrange
		given(userPersistencePort.findById(anyString())).willReturn(Optional.of(user));
		given(userValidationService.validate(user)).willReturn(new ValidationResult(false, "errorMsg"));

		// When / Act
		assertThrows(DomainException.class, () -> {
			service.updateUser(UUID.randomUUID().toString(), user);
		});

		// Then / Assert
		verify(userPersistencePort, never()).save(any(User.class));
	}
	
	@DisplayName("JUnit test for Given Invalid Cpf when Update User then throws Exception")
	@Test
	void testGivenInvalidCpf_WhenUpdateUser_thenThrowsException() {
		
		User cpfInvalidUser = User.builder()
					.name("Marcos")
					.cpf("00000000000")
					.email("marcos@mercadolivre.com")
					.birthdate(LocalDate.of(1989, 6, 3))
					.build();

		// Given / Arrange
		given(userPersistencePort.findById(anyString())).willReturn(Optional.of(cpfInvalidUser));
		given(userValidationService.validate(cpfInvalidUser)).willReturn(new ValidationResult(false, "errorMsg"));

		// When / Act
		assertThrows(DomainException.class, () -> {
			service.updateUser(UUID.randomUUID().toString(), cpfInvalidUser);
		});

		// Then / Assert
		verify(userPersistencePort, never()).save(any(User.class));
	}
	
	@DisplayName("JUnit test for Given Age Under 18 when Update User then throws Exception")
	@Test
	void testGivenAgeUnder18_WhenUpdateUser_thenThrowsException() {

		// Given / Arrange
		given(userPersistencePort.findById(anyString())).willReturn(Optional.of(user));
		given(userValidationService.validate(user)).willReturn(new ValidationResult(false, "errorMsg"));
		
		user.setBirthdate(LocalDate.now());

		// When / Act
		assertThrows(DomainException.class, () -> {
			service.updateUser(UUID.randomUUID().toString(), user);
		});

		// Then / Assert
		verify(userPersistencePort, never()).save(any(User.class));
	}
	
	@DisplayName("JUnit test for Given UserId when findById then Return User Object")
    @Test
    void testGivenUserId_WhenFindById_thenReturnUserObject() {
        
        // Given / Arrange
        given(userPersistencePort.findById(any())).willReturn(Optional.of(user));
        
        // When / Act
        User savedUser = service.findById(UUID.randomUUID().toString());
        
        // Then / Assert
        assertNotNull(savedUser);
        assertEquals("Matheus", savedUser.getName());
    }
	
	@DisplayName("JUnit test for Given Empty Name when find then Return All User Objects")
    @Test
    void testGivenEmptyName_WhenFind_thenReturnAllUserObjects() {
		
		User user1 = User.builder()
					.id(UUID.randomUUID().toString())
					.name("Leandro")
					.cpf("54447091046")
					.email("leandro@mercadolivre.com")
					.birthdate(LocalDate.of(1998, 7, 1))
					.createdAt(LocalDateTime.now())
					.build();
		
		User user2 = User.builder()
					.id(UUID.randomUUID().toString())
					.name("Leonardo")
					.cpf("35774128016")
					.email("leandro@mercadolivre.com")
					.birthdate(LocalDate.of(1998, 8, 1))
					.createdAt(LocalDateTime.now())
					.build();
		
		Pageable pageable = PageRequest.ofSize(10);
        
        // Given / Arrange
        given(userPersistencePort.findAll(pageable)).willReturn(new PageImpl<>(List.of(user1, user2), pageable, 2));
        
        // When / Act
        Page<User> result = service.find(null, pageable);
        
        // Then / Assert
        assertNotNull(result);
        assertEquals(2, result.getNumberOfElements());
        assertEquals("Leandro", result.getContent().get(0).getName());
        assertEquals("Leonardo", result.getContent().get(1).getName());
    }
	
	@DisplayName("JUnit test for Given Name when find then Return User Object with name")
    @Test
    void testGivenName_WhenFind_thenReturnUserObjectWithName() {
		
		User user1 = User.builder()
					.id(UUID.randomUUID().toString())
					.name("Leandro")
					.cpf("54447091046")
					.email("leandro@mercadolivre.com")
					.birthdate(LocalDate.of(1998, 7, 1))
					.createdAt(LocalDateTime.now())
					.build();
		
		Pageable pageable = PageRequest.ofSize(10);
        
        // Given / Arrange
        given(userPersistencePort.findByName("Leandro", pageable)).willReturn(new PageImpl<>(List.of(user1), pageable, 1));
        
        // When / Act
        Page<User> result = service.find("Leandro", pageable);
        
        // Then / Assert
        assertNotNull(result);
        assertEquals(1, result.getNumberOfElements());
        assertEquals("Leandro", result.getContent().get(0).getName());
    }
	
	@DisplayName("JUnit test for Given UserId when Delete User then do Nothing")
    @Test
    void testGivenUserID_WhenDeleteUser_thenDoNothing() {
        
        // Given / Arrange
        given(userPersistencePort.findById(anyString())).willReturn(Optional.of(user));
        willDoNothing().given(userPersistencePort).delete(user);
        
        // When / Act
        service.delete(UUID.randomUUID().toString());
        
        // Then / Assert
        verify(userPersistencePort, times(1)).delete(user);
    }  

}
