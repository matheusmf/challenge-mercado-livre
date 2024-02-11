package br.com.matheusmf.challenge.domain.application.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.matheusmf.challenge.domain.DomainException;
import br.com.matheusmf.challenge.domain.User;
import br.com.matheusmf.challenge.domain.application.request.UserRequest;
import br.com.matheusmf.challenge.domain.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserService service;
	
	@Autowired
	public UserController(UserService service) {
		this.service = service;
	}
	
	@Operation(summary = "Busca um usuário por id", description = "Retorna um usuário por id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna um usuário com o id enviado"),
			@ApiResponse(responseCode = "404", description = "Não foi encontradoa um usuário com o id enviado"),
			@ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
	})
	@GetMapping(value = "/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> findById(@PathVariable(value = "id") String id) {
		try {
			return ResponseEntity.ok(service.findById(id));
        } catch (DomainException e) {
            return ResponseEntity.notFound().build();
        }
	}
	
	@Operation(summary = "Cria um novo usuário", description = "Cria um novo usuário com os dados enviados no corpo requisição")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna o usuário criado"),
			@ApiResponse(responseCode = "400", description = "Não foi possível criar um usuário porque os dados não passaram nas validações"),
			@ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
	})
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody UserRequest userRequest) {
		try {
			return ResponseEntity.ok(service.createUser(userRequest.toUser()));
        } catch (DomainException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
	}
	
	@Operation(summary = "Atualiza um usuário", description = "Atualiza um usuário com os dados enviados no corpo requisição")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna o usuário atualizado"),
			@ApiResponse(responseCode = "400", description = "Não foi possível atualizar o usuário porque os dados não passaram nas validações"),
			@ApiResponse(responseCode = "404", description = "Não foi encontradoa um usuário com o id enviado"),
			@ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
	})
	@PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@PathVariable(value = "id") String id, @RequestBody UserRequest userRequest) {
		try {
			return ResponseEntity.ok(service.updateUser(id, userRequest.toUser()));
        } catch (DomainException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
	}
	
	@Operation(summary = "Apaga um usuário", description = "Apaga um usuário através do id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "O usuário foi deletado"),
			@ApiResponse(responseCode = "404", description = "Não foi encontradoa um usuário com o id enviado"),
			@ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
	})
	@DeleteMapping(value = "/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> delete(@PathVariable(value = "id") String id) {
		try {
			service.delete(id);
		} catch (DomainException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}

}
