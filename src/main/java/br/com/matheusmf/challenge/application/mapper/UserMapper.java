package br.com.matheusmf.challenge.application.mapper;

import br.com.matheusmf.challenge.application.dto.request.UserRequest;
import br.com.matheusmf.challenge.application.dto.response.UserResponse;
import br.com.matheusmf.challenge.core.domain.User;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Singleton
@Named("userMapper")
public class UserMapper {
	
	public User toUser(UserRequest userRequest) {
		return User.builder()
				.name(userRequest.getName())
				.cpf(userRequest.getCpf())
				.email(userRequest.getEmail())
				.birthdate(userRequest.getBirthdate())
				.build();
	}
	
	public UserResponse toResponse(User user) {
		return UserResponse.builder()
				.id(user.getId())
				.name(user.getName())
				.cpf(user.getCpf())
				.email(user.getEmail())
				.birthdate(user.getBirthdate())
				.createdAt(user.getCreatedAt())
				.lastUpdatedAt(user.getLastUpdatedAt())
				.build();
	}

}
