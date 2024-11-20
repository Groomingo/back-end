package com.example.groomingo.domain.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.groomingo.domain.auth.dto.SignUpRequest;
import com.example.groomingo.domain.user.domain.KakaoEntity;
import com.example.groomingo.domain.user.domain.Role;
import com.example.groomingo.domain.user.domain.UserEntity;
import com.example.groomingo.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

	@Value("${kakao.fixed-password}")
	private static String FIXED_KAKAO_PASSWORD;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	public UserEntity save(SignUpRequest request) {
		return userRepository.save(
			UserEntity.builder()
				.email(request.email())
				.role(Role.USER)
				.password(passwordEncoder.encode(request.password()))
				.build());
	}

	public void saveByKakao(KakaoEntity kakaoEntity) {
		userRepository.save(
			UserEntity.builder()
				.role(Role.USER)
				.password(passwordEncoder.encode(FIXED_KAKAO_PASSWORD))
				.build()
		);
	}
}
