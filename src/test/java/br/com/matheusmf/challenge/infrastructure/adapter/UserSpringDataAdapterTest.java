package br.com.matheusmf.challenge.infrastructure.adapter;

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

import br.com.matheusmf.challenge.core.domain.User;
import br.com.matheusmf.challenge.infrastructure.out.springdata.adapter.UserSpringDataAdapter;
import br.com.matheusmf.challenge.infrastructure.out.springdata.mapper.UserDocumentMapper;
import br.com.matheusmf.challenge.infrastructure.out.springdata.persistence.document.UserDocument;
import br.com.matheusmf.challenge.infrastructure.out.springdata.persistence.repository.SpringDataMongoUserRepository;

@SpringJUnitConfig
@SpringBootTest
public class UserSpringDataAdapterTest {
	
	@Autowired
    private SpringDataMongoUserRepository mongoUserRepository;

    @Autowired
    private UserSpringDataAdapter userSpringDataAdapter;
    
    private UserDocument userDocument0;
    
    @BeforeEach
	public void setup() {
        // Given / Arrange
		userDocument0 = UserDocument.builder()
						.name("Matheus")
						.cpf("28106132064")
						.email("matheus@mercadolivre.com")
						.birthdate(LocalDate.of(1989, 6, 3))
						.build();
		mongoUserRepository.save(userDocument0);
	}

    @AfterEach
    void cleanUp() {
    	mongoUserRepository.deleteAll();
    }
    
    @DisplayName("JUnit test for Given UserId when findById then Return User Object")
    @Test
    void testGivenUserId_WhenFindById_thenReturnUserObject() {
        
        // When / Act
        Optional<User> savedUser = userSpringDataAdapter.findById(userDocument0.getId());
        
        // Then / Assert
        assertNotNull(savedUser);
        assertEquals("Matheus", savedUser.get().getName());
    }
    
    @DisplayName("JUnit test for Given Name when findByName then Return User Object")
    @Test
    void testGivenName_WhenFindByName_thenReturnUserObject() {
        
        // When / Act
        Page<User> pageUser = userSpringDataAdapter.findByName(userDocument0.getName(), PageRequest.ofSize(2));
        
        // Then / Assert
        assertNotNull(pageUser);
        assertEquals("Matheus", pageUser.getContent().get(0).getName());
    }
    
    @DisplayName("JUnit test for Given Name when findByName then Return User Object")
    @Test
    void testGivenNothing_WhenFindAll_thenReturnAllUserObject() {
    	// Given / Arrange
    	UserDocument userDocument1 = UserDocument.builder()
									.id(UUID.randomUUID().toString())
									.name("Leonardo")
									.cpf("91252573073")
									.email("leonardo@mercadolivre.com")
									.birthdate(LocalDate.of(1989, 6, 3))
									.build();
        mongoUserRepository.save(userDocument1);
        
        // When / Act
        Page<User> pageUser = userSpringDataAdapter.findAll(PageRequest.ofSize(10));
        
        // Then / Assert
        assertNotNull(pageUser);
        assertEquals(2, pageUser.getNumberOfElements());
    }
    
    @DisplayName("JUnit test for Given Cpf when findByCpf then Return User Object")
    @Test
    void testGivenCpf_WhenFindByCpf_thenReturnUserObject() {
        
        // When / Act
        Optional<User> savedUser = userSpringDataAdapter.findByCpf(userDocument0.getCpf());
        
        // Then / Assert
        assertNotNull(savedUser);
        assertEquals("Matheus", savedUser.get().getName());
        assertEquals("28106132064", savedUser.get().getCpf());
    }
    
    @DisplayName("JUnit test for Given Cpf and Id when findByCpfAndIdNot then Return User Object")
    @Test
    void testGivenCpfAndId_WhenFindByCpfAndIdNot_thenReturnUserObject() {
        
        // When / Act
        Optional<User> savedUser = userSpringDataAdapter.findByCpfAndIdNot(userDocument0.getCpf(), UUID.randomUUID().toString());
        
        // Then / Assert
        assertNotNull(savedUser);
        assertEquals("Matheus", savedUser.get().getName());
        assertEquals("28106132064", savedUser.get().getCpf());
    }
    
    @DisplayName("JUnit test for Given Email when findByEmail then Return User Object")
    @Test
    void testGivenEmail_WhenFindByEmail_thenReturnUserObject() {
        
        // When / Act
        Optional<User> savedUser = userSpringDataAdapter.findByEmail(userDocument0.getEmail());
        
        // Then / Assert
        assertNotNull(savedUser);
        assertEquals("Matheus", savedUser.get().getName());
        assertEquals("matheus@mercadolivre.com", savedUser.get().getEmail());
    }
    
    @DisplayName("JUnit test for Given Email and Id when findByEmailAndIdNot then Return User Object")
    @Test
    void testGivenEmailAndId_WhenFindByEmailAndIdNot_thenReturnUserObject() {
        
        // When / Act
        Optional<User> savedUser = userSpringDataAdapter.findByEmailAndIdNot(userDocument0.getEmail(), UUID.randomUUID().toString());
        
        // Then / Assert
        assertNotNull(savedUser);
        assertEquals("Matheus", savedUser.get().getName());
        assertEquals("matheus@mercadolivre.com", savedUser.get().getEmail());
    }
    
    @DisplayName("JUnit test for Given User Object when Save then Return Saved User")
    @Test
    void testGivenUserObject_whenSave_thenReturnSavedUser() {
        
        // Given / Arrange
    	UserDocument userDocument2 = UserDocument.builder()
				.id(UUID.randomUUID().toString())
				.name("Gabriela")
				.cpf("88763050099")
				.email("gabriela@mercadolivre.com")
				.birthdate(LocalDate.of(1989, 6, 3))
				.build();
        
        // When / Act
        User savedUser = userSpringDataAdapter.save(new UserDocumentMapper().toDomain(userDocument2));
        
        // Then / Assert
        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals("Gabriela", savedUser.getName());
    }
    
    @DisplayName("JUnit test for Given User Object when Delete then Remove User")
    @Test
    void testGivenUserObject_whenDelete_thenRemoveUser() {
        
        // When / Act
    	userSpringDataAdapter.delete(new UserDocumentMapper().toDomain(userDocument0));
        
        Optional<User> userOptional = userSpringDataAdapter.findById(userDocument0.getId());
        
        // Then / Assert
        assertTrue(userOptional.isEmpty());
    }

}
