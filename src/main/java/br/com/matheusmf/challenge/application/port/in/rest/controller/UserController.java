package br.com.matheusmf.challenge.application.port.in.rest.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.matheusmf.challenge.application.dto.request.UserRequest;
import br.com.matheusmf.challenge.application.dto.response.UserResponse;
import br.com.matheusmf.challenge.application.mapper.UserMapper;
import br.com.matheusmf.challenge.application.port.in.rest.api.UserApi;
import br.com.matheusmf.challenge.core.domain.DomainException;
import br.com.matheusmf.challenge.core.domain.User;
import br.com.matheusmf.challenge.core.port.in.UserServicePort;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController implements UserApi {
	
	private final UserServicePort service;
	private final UserMapper mapper;
	
	@Override
	public ResponseEntity<UserResponse> findById(@PathVariable(value = "id") String id) {
		try {
			return ResponseEntity.ok(mapper.toResponse(service.findById(id)));
        } catch (DomainException e) {
            return ResponseEntity.notFound().build();
        }
	}
	
	@Override
	public ResponseEntity<?> create(@RequestBody UserRequest userRequest) {
		try {
			User user = mapper.toUser(userRequest);
			UserResponse response = mapper.toResponse(service.createUser(user));
			return ResponseEntity.ok(response);
        } catch (DomainException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
	}
	
	@Override
	public ResponseEntity<?> update(@PathVariable(value = "id") String id, @RequestBody UserRequest userRequest) {
		try {
			User user = mapper.toUser(userRequest);
			UserResponse response = mapper.toResponse(service.updateUser(id, user));
			return ResponseEntity.ok(response);
        } catch (DomainException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
	}
	
	@Override
	public ResponseEntity<?> delete(@PathVariable(value = "id") String id) {
		try {
			service.delete(id);
		} catch (DomainException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}
	
	@Override
	public ResponseEntity<Page<UserResponse>> find(@RequestParam(value = "name", required = false) String name, Pageable pageable) {
		return ResponseEntity.ok(service.find(name, pageable).map(mapper::toResponse));
	}

}
