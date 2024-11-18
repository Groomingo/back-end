package com.example.groomingo.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.groomingo.common.exception.ExceptionState;
import com.example.groomingo.common.exception.detail.UserException;
import com.example.groomingo.domain.user.domain.UserEntity;
import com.example.groomingo.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserFindService {

	private final UserRepository userRepository;

	public UserEntity getByEmail(String email) {
		return userRepository.findByEmail(email)
			.orElseThrow(() -> new UserException(ExceptionState.USER_NOT_FOUND));
	}

	public boolean isAlreadyMember(String email) {
		return userRepository.existsByEmail(email);
	}
}
