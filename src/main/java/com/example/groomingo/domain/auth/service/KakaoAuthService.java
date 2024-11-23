package com.example.groomingo.domain.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.groomingo.common.jwt.JwtTokenProvider;
import com.example.groomingo.domain.auth.dto.KakaoInfo;
import com.example.groomingo.domain.user.domain.KakaoEntity;
import com.example.groomingo.domain.user.domain.UserEntity;
import com.example.groomingo.domain.user.service.KakaoService;
import com.example.groomingo.domain.user.service.UserFindService;
import com.example.groomingo.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KakaoAuthService {

	@Value("${kakao.fixed-password}")
	private static String FIXED_KAKAO_PASSWORD;
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManager authenticationManager;
	private final UserFindService userFindService;
	private final UserService userService;
	private final KakaoService kakaoService;
	private final KakaoClientService kakaoClientService;

	@Transactional
	public String loginByKakao(String code) {
		String kakaoToken = kakaoClientService.authorize(code).access_token();
		KakaoInfo kakaoInfo = kakaoClientService.getUserInfo(kakaoToken);
		Optional<UserEntity> userEntityOptional = userFindService.findByKakaoId(kakaoInfo.id());
		UserEntity userEntity = userEntityOptional.orElseGet(() -> signUpByKakao(kakaoInfo));
		return kakaoLogin(userEntity);
	}

	private UserEntity signUpByKakao(KakaoInfo kakaoInfo) {
		KakaoEntity savedKakaoEntity = kakaoService.save(KakaoEntity.toEntity(kakaoInfo));
		UserEntity savedUserEntity = userService.saveByKakao(savedKakaoEntity, FIXED_KAKAO_PASSWORD);
		log.info("[카카오 회원가입 완료] kakaoInfo: {}", kakaoInfo);
		return savedUserEntity;
	}

	private String kakaoLogin(UserEntity userEntity) {
		Authentication authentication = authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(userEntity.getKakaoEntity().getId(), FIXED_KAKAO_PASSWORD));

		return jwtTokenProvider.generateToken(authentication);
	}

}
