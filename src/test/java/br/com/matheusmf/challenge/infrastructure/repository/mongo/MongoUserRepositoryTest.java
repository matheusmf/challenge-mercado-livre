package br.com.matheusmf.challenge.infrastructure.repository.mongo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import br.com.matheusmf.challenge.domain.User;
import br.com.matheusmf.challenge.domain.repository.UserRepository;

@SpringJUnitConfig
@SpringBootTest
public class MongoUserRepositoryTest {
	
	@Autowired
    private SpringDataMongoUserRepository mongoUserRepository;

    @Autowired
    private UserRepository userRepository;
    
    private User user0;
    
    @BeforeEach
	public void setup() {
		user0 = new User(
			new User.Builder()
				.name("Matheus")
				.cpf("28106132064")
				.email("matheus@mercadolivre.com")
				.birthdate(LocalDate.of(1989, 6, 3)));
	}

    @AfterEach
    void cleanUp() {
    	mongoUserRepository.deleteAll();
    }
    
    @DisplayName("JUnit test for Given UserId when findById then Return User Object")
    @Test
    void testGivenUserId_WhenFindById_thenReturnUserObject() {
        
        // Given / Arrange
    	user0.setId(UUID.randomUUID().toString());
        mongoUserRepository.save(user0);
        
        // When / Act
        Optional<User> savedUser = userRepository.findById(user0.getId());
        
        // Then / Assert
        assertNotNull(savedUser);
        assertEquals("Matheus", savedUser.get().getName());
    }
    
    @DisplayName("JUnit test for Given Name when findByName then Return User Object")
    @Test
    void testGivenName_WhenFindByName_thenReturnUserObject() {
        
        // Given / Arrange
    	user0.setId(UUID.randomUUID().toString());
        mongoUserRepository.save(user0);
        
        // When / Act
        Page<User> pageUser = userRepository.findByName(user0.getName(), PageRequest.ofSize(2));
        
        // Then / Assert
        assertNotNull(pageUser);
        assertEquals("Matheus", pageUser.getContent().get(0).getName());
    }
    
    @DisplayName("JUnit test for Given Name when findByName then Return User Object")
    @Test
    void testGivenNothing_WhenFindAll_thenReturnAllUserObject() {
        
        // Given / Arrange
    	user0.setId(UUID.randomUUID().toString());
        mongoUserRepository.save(user0);
        
        User user1 = new User(
    			new User.Builder()
    			    .id(UUID.randomUUID().toString())
    				.name("Leonardo")
    				.cpf("91252573073")
    				.email("leonardo@mercadolivre.com")
    				.birthdate(LocalDate.of(1989, 6, 3)));
        mongoUserRepository.save(user1);
        
        // When / Act
        Page<User> pageUser = userRepository.findAll(PageRequest.ofSize(10));
        
        // Then / Assert
        assertNotNull(pageUser);
        assertEquals(2, pageUser.getNumberOfElements());
    }
    
    @DisplayName("JUnit test for Given Cpf when findByCpf then Return User Object")
    @Test
    void testGivenCpf_WhenFindByCpf_thenReturnUserObject() {
        
        // Given / Arrange
    	user0.setId(UUID.randomUUID().toString());
        mongoUserRepository.save(user0);
        
        // When / Act
        Optional<User> savedUser = userRepository.findByCpf(user0.getCpf());
        
        // Then / Assert
        assertNotNull(savedUser);
        assertEquals("Matheus", savedUser.get().getName());
        assertEquals("28106132064", savedUser.get().getCpf());
    }
    
    @DisplayName("JUnit test for Given Cpf and Id when findByCpfAndIdNot then Return User Object")
    @Test
    void testGivenCpfAndId_WhenFindByCpfAndIdNot_thenReturnUserObject() {
        
        // Given / Arrange
    	user0.setId(UUID.randomUUID().toString());
        mongoUserRepository.save(user0);
        
        // When / Act
        Optional<User> savedUser = userRepository.findByCpfAndIdNot(user0.getCpf(), UUID.randomUUID().toString());
        
        // Then / Assert
        assertNotNull(savedUser);
        assertEquals("Matheus", savedUser.get().getName());
        assertEquals("28106132064", savedUser.get().getCpf());
    }
    
    @DisplayName("JUnit test for Given Email when findByEmail then Return User Object")
    @Test
    void testGivenEmail_WhenFindByEmail_thenReturnUserObject() {
        
        // Given / Arrange
    	user0.setId(UUID.randomUUID().toString());
        mongoUserRepository.save(user0);
        
        // When / Act
        Optional<User> savedUser = userRepository.findByEmail(user0.getEmail());
        
        // Then / Assert
        assertNotNull(savedUser);
        assertEquals("Matheus", savedUser.get().getName());
        assertEquals("matheus@mercadolivre.com", savedUser.get().getEmail());
    }
    
    @DisplayName("JUnit test for Given Email and Id when findByEmailAndIdNot then Return User Object")
    @Test
    void testGivenEmailAndId_WhenFindByEmailAndIdNot_thenReturnUserObject() {
        
        // Given / Arrange
    	user0.setId(UUID.randomUUID().toString());
        mongoUserRepository.save(user0);
        
        // When / Act
        Optional<User> savedUser = userRepository.findByEmailAndIdNot(user0.getEmail(), UUID.randomUUID().toString());
        
        // Then / Assert
        assertNotNull(savedUser);
        assertEquals("Matheus", savedUser.get().getName());
        assertEquals("matheus@mercadolivre.com", savedUser.get().getEmail());
    }
    
    @DisplayName("JUnit test for Given User Object when Save then Return Saved User")
    @Test
    void testGivenUserObject_whenSave_thenReturnSavedUser() {
        
        // Given / Arrange
    	user0.setId(UUID.randomUUID().toString());
        
        // When / Act
        User savedUser = userRepository.save(user0);
        
        // Then / Assert
        assertNotNull(savedUser);
        assertEquals(user0.getId(), savedUser.getId());
    }
    
    @DisplayName("JUnit test for Given User Object when Delete then Remove User")
    @Test
    void testGivenUserObject_whenDelete_thenRemoveUser() {
        
        // Given / Arrange
    	user0.setId(UUID.randomUUID().toString());
    	userRepository.save(user0);
        
        // When / Act
    	userRepository.delete(user0);
        
        Optional<User> userOptional = userRepository.findById(user0.getId());
        
        // Then / Assert
        assertTrue(userOptional.isEmpty());
    }

}
