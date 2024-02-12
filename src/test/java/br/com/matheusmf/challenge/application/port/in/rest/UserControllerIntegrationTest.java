package br.com.matheusmf.challenge.application.port.in.rest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.matheusmf.challenge.application.dto.request.UserRequest;
import br.com.matheusmf.challenge.application.dto.response.UserResponse;
import br.com.matheusmf.challenge.infrastructure.out.springdata.persistence.repository.SpringDataMongoUserRepository;
import br.com.matheusmf.challenge.utils.PageModule;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerIntegrationTest {
	
	@Autowired
    private SpringDataMongoUserRepository mongoUserRepository;
	
	private static final int SERVER_PORT = 8080;
	private static final String CONTENT_TYPE_JSON = "application/json"; 
	
	private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static UserRequest userRequest0;
    private static String userId;
    
    @BeforeAll
    public void setup() {
    	mongoUserRepository.deleteAll();
        
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new PageModule());
        
        specification = new RequestSpecBuilder()
            .setBasePath("/users")
            .setPort(SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();
        
        userRequest0 = new UserRequest(); 
        userRequest0.setName("Matheus");
        userRequest0.setCpf("28106132064");
        userRequest0.setEmail("matheus@mercadolivre.com");
        userRequest0.setBirthdate(LocalDate.of(1989, 6, 3));
    }
    
    
    @Test
    @Order(1)
    @DisplayName("JUnit integration given User Object when Create one User should Return an User Object")
    void integrationTestGivenUserObject_when_CreateOnePerson_ShouldReturnAnUserObject() throws JsonMappingException, JsonProcessingException {
        
        var content = given().spec(specification)
                    .contentType(CONTENT_TYPE_JSON)
                    .body(userRequest0)
                .when()
                    .post()
                .then()
                    .statusCode(200)
                        .extract()
                            .body()
                                .asString();
        
        UserResponse createdUser = objectMapper.readValue(content, UserResponse.class);
        
        userId = createdUser.getId();
        
        assertNotNull(createdUser);
        
        assertNotNull(createdUser.getId());
        assertNotNull(createdUser.getName());
        assertNotNull(createdUser.getCpf());
        assertNotNull(createdUser.getEmail());
        assertNotNull(createdUser.getBirthdate());
        assertNotNull(createdUser.getCreatedAt());
        
        assertEquals("Matheus", createdUser.getName());
        assertEquals("matheus@mercadolivre.com", createdUser.getEmail());
    }
    
    @Test
    @Order(2)
    @DisplayName("JUnit integration given User Object when Update one User should Return an Updated User Object")
    void integrationTestGivenUserObject_when_UpdateOneUser_ShouldReturnAnUpdatedUserObject() throws JsonMappingException, JsonProcessingException {
        
    	userRequest0.setName("Matheus Farias");
    	userRequest0.setEmail("matheusfarias@mercadolivre.com");
        
        var content = given().spec(specification)
        		    .pathParam("id", userId)
                    .contentType(CONTENT_TYPE_JSON)
                    .body(userRequest0)
                .when()
                    .patch("{id}")
                .then()
                    .statusCode(200)
                        .extract()
                            .body()
                                .asString();
        
        UserResponse updatedUser = objectMapper.readValue(content, UserResponse.class);
        
        assertNotNull(updatedUser);
        
        assertNotNull(updatedUser.getId());
        assertNotNull(updatedUser.getName());
        assertNotNull(updatedUser.getCpf());
        assertNotNull(updatedUser.getEmail());
        assertNotNull(updatedUser.getBirthdate());
        assertNotNull(updatedUser.getCreatedAt());
        assertNotNull(updatedUser.getLastUpdatedAt());
        
        assertEquals("Matheus Farias", updatedUser.getName());
        assertEquals("matheusfarias@mercadolivre.com", updatedUser.getEmail());
    }
    
    @Test
    @Order(3)
    @DisplayName("JUnit integration given User Id when findById should Return a User Object")
    void integrationTestGivenUserId_when_findById_ShouldReturnAnUserObject() throws JsonMappingException, JsonProcessingException {
        
        var content = given().spec(specification)
                .pathParam("id", userId)
            .when()
                .get("{id}")
            .then()
                .statusCode(200)
                    .extract()
                        .body()
                            .asString();
        
        UserResponse foundUser = objectMapper.readValue(content, UserResponse.class);
        
        assertNotNull(foundUser);
        
        assertNotNull(foundUser.getId());
        assertNotNull(foundUser.getName());
        assertNotNull(foundUser.getCpf());
        assertNotNull(foundUser.getEmail());
        assertNotNull(foundUser.getBirthdate());
        assertNotNull(foundUser.getCreatedAt());
        assertNotNull(foundUser.getLastUpdatedAt());
        
        assertEquals("Matheus Farias", foundUser.getName());
        assertEquals("matheusfarias@mercadolivre.com", foundUser.getEmail());
    }
    
    @Test
    @Order(4)
    @DisplayName("JUnit integration given Nothing when find should Return a Users List")
    void integrationTestGivenNothing_when_find_ShouldReturnAnUsersList() throws JsonMappingException, JsonProcessingException {
        
        UserRequest anotherUser = new UserRequest();
        anotherUser.setName("Gabriela");
        anotherUser.setCpf("65791518030");
        anotherUser.setEmail("gabriela@mercadolivre.com");
        anotherUser.setBirthdate(LocalDate.of(1989, 6, 3));
        
        given().spec(specification)
            .contentType(CONTENT_TYPE_JSON)
            .body(anotherUser)
        .when()
            .post()
        .then()
            .statusCode(200);
        
        var content = given().spec(specification)
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                        .asString();
        
        Page<UserResponse> page = objectMapper.readValue(content, new TypeReference<Page<UserResponse>>() {});
        
        assertEquals(2, page.getNumberOfElements());
        
        UserResponse foundUserOne = page.getContent().get(0);
        
        assertNotNull(foundUserOne);
        
        assertNotNull(foundUserOne.getId());
        assertNotNull(foundUserOne.getName());
        assertNotNull(foundUserOne.getCpf());
        assertNotNull(foundUserOne.getEmail());
        
        assertEquals("Matheus Farias", foundUserOne.getName());
        assertEquals("matheusfarias@mercadolivre.com", foundUserOne.getEmail());
        
        UserResponse foundUserTwo = page.getContent().get(1);
        
        assertNotNull(foundUserTwo);
        
        assertNotNull(foundUserTwo.getId());
        assertNotNull(foundUserTwo.getName());
        assertNotNull(foundUserTwo.getCpf());
        assertNotNull(foundUserTwo.getEmail());
        
        assertEquals("Gabriela", foundUserTwo.getName());
        assertEquals("gabriela@mercadolivre.com", foundUserTwo.getEmail());
    }
    
    @Test
    @Order(5)
    @DisplayName("JUnit integration given User Name when find should Return a Filtered Users List")
    void integrationTestGivenName_when_find_ShouldReturnAFilteredUsersList() throws JsonMappingException, JsonProcessingException {
        
        var content = given().spec(specification)
        		.param("name", "gabriela")
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                        .asString();
        
        Page<UserResponse> page = objectMapper.readValue(content, new TypeReference<Page<UserResponse>>() {});
        
        assertEquals(1, page.getNumberOfElements());
        
        UserResponse foundUser = page.getContent().get(0);
        
        assertNotNull(foundUser);
        
        assertNotNull(foundUser.getId());
        assertNotNull(foundUser.getName());
        assertNotNull(foundUser.getCpf());
        assertNotNull(foundUser.getEmail());
        
        assertEquals("Gabriela", foundUser.getName());
        assertEquals("gabriela@mercadolivre.com", foundUser.getEmail());
    }
    
    @Test
    @Order(6)
    @DisplayName("JUnit integration given User Id when delete should Return No Content")
    void integrationTestGivenUserId_when_delete_ShouldReturnNoContent() throws JsonMappingException, JsonProcessingException {
        
        given().spec(specification)
                .pathParam("id", userId)
            .when()
                .delete("{id}")
            .then()
                .statusCode(204);
        
        var content = given().spec(specification)
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                        .asString();
        
        Page<UserResponse> page = objectMapper.readValue(content, new TypeReference<Page<UserResponse>>() {});
        
        assertEquals(1, page.getNumberOfElements());
    }

}
