package br.com.matheusmf.challenge.application.port.in.rest.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.matheusmf.challenge.application.dto.request.UserRequest;
import br.com.matheusmf.challenge.application.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface UserApi {
	
	@Operation(summary = "Busca um usuário por id", description = "Retorna um usuário por id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna um usuário com o id enviado"),
			@ApiResponse(responseCode = "404", description = "Não foi encontradoa um usuário com o id enviado")
	})
	@GetMapping(value = "/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UserResponse> findById(@PathVariable(value = "id") String id);
	
	@Operation(summary = "Cria um novo usuário", description = "Cria um novo usuário com os dados enviados no corpo requisição")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna o usuário criado"),
			@ApiResponse(responseCode = "400", description = "Não foi possível criar um usuário porque os dados não passaram nas validações")
	})
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> create(@RequestBody UserRequest userRequest);
	
	@Operation(summary = "Atualiza um usuário", description = "Atualiza um usuário com os dados enviados no corpo requisição")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna o usuário atualizado"),
			@ApiResponse(responseCode = "400", description = "Não foi possível atualizar o usuário porque os dados não passaram nas validações"),
			@ApiResponse(responseCode = "404", description = "Não foi encontradoa um usuário com o id enviado")
	})
	@PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> update(@PathVariable(value = "id") String id, @RequestBody UserRequest userRequest);
	
	@Operation(summary = "Apaga um usuário", description = "Apaga um usuário através do id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "O usuário foi deletado"),
			@ApiResponse(responseCode = "404", description = "Não foi encontradoa um usuário com o id enviado")
	})
	@DeleteMapping(value = "/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> delete(@PathVariable(value = "id") String id);
	
	@Operation(summary = "Busca todos os usuários podendo filtrar por nome", description = "Retorna uma lista de usuários")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna uma lista de usuários")
	})
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Page<UserResponse>> find(@RequestParam(value = "name", required = false) String name, Pageable pageable);

}
