package com.example.groomingo.domain.auth.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.groomingo.common.exception.ExceptionState;
import com.example.groomingo.common.exception.detail.UserException;
import com.example.groomingo.common.jwt.JwtTokenProvider;
import com.example.groomingo.domain.auth.dto.CheckIsMemberRequest;
import com.example.groomingo.domain.auth.dto.KakaoInfo;
import com.example.groomingo.domain.auth.dto.KakaoLogin;
import com.example.groomingo.domain.auth.dto.Login;
import com.example.groomingo.domain.user.domain.KakaoEntity;
import com.example.groomingo.domain.user.domain.UserEntity;
import com.example.groomingo.domain.auth.dto.SignUpRequest;
import com.example.groomingo.domain.user.service.KakaoService;
import com.example.groomingo.domain.user.service.UserFindService;
import com.example.groomingo.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManager authenticationManager;
	private final UserFindService userFindService;
	private final UserService userService;
	private final KakaoService kakaoService;
	private final KakaoClientService kakaoClientService;

	public String login(Login.Request request) {
		Authentication authentication = authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

		return jwtTokenProvider.generateToken(authentication);
	}

	public boolean checkIsMember(CheckIsMemberRequest request) {
		return userFindService.isAlreadyMember(request.email());
	}

	@Transactional
	public String signUp(SignUpRequest request) {
		if (userFindService.isAlreadyMember(request.email())) {
			throw new UserException(ExceptionState.DUPLICATED_EMAIL);
		}

		UserEntity userEntity = userService.save(request);
		return userEntity.getEmail();
	}

	@Transactional
	public void signUpByKakao(KakaoInfo kakaoInfo) {
		KakaoEntity savedKakaoEntity = kakaoService.save(KakaoEntity.toEntity(kakaoInfo));
		userService.saveByKakao(savedKakaoEntity);
		log.info("[카카오 회원가입 완료] kakaoInfo: {}", kakaoInfo);
	}

	public void loginByKakao(String code) {
		String kakaoToken = kakaoClientService.authorize(code).access_token();
		KakaoInfo kakaoInfo = kakaoClientService.getUserInfo(kakaoToken);
		Optional<UserEntity> userEntityOptional = userFindService.findByKakaoId(kakaoInfo.id());
		if (userEntityOptional.isEmpty()) {
			signUpByKakao(kakaoInfo);
			//TODO 토큰 발행 후 응답 로직
		} else {
			//TODO 토큰 발행 후 응답 로직
		}
	}
}
