package com.example.groomingo.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.groomingo.common.exception.ExceptionState;
import com.example.groomingo.common.exception.detail.UserException;
import com.example.groomingo.domain.user.domain.Role;
import com.example.groomingo.domain.user.domain.UserEntity;
import com.example.groomingo.domain.user.dto.SignUpRequest;
import com.example.groomingo.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	@Transactional
	public String signUp(SignUpRequest request) {
		validateIsDuplicated(request.email());
		UserEntity savedUserEntity = save(request);
		return savedUserEntity.getEmail();
	}

	private UserEntity save(SignUpRequest request) {
		return userRepository.save(
			UserEntity.builder()
			.email(request.email())
			.role(Role.USER)
			.password(passwordEncoder.encode(request.password()))
			.build());
	}

	private void validateIsDuplicated(String email) {
		userRepository.findByEmail(email).ifPresent(user -> {
			throw new UserException(ExceptionState.DUPLICATED_EMAIL);
		});
	}
}
