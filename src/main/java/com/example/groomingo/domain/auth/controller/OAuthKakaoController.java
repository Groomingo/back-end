package com.example.groomingo.domain.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.groomingo.domain.auth.service.KakaoAuthService;
import com.example.groomingo.domain.auth.service.KakaoClientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class OAuthKakaoController {

	private final KakaoClientService kakaoClientService;
	private final KakaoAuthService kakaoAuthService;

	/**
	 * 카카오 인증코드 요청
	 */
	@GetMapping("/kakao/authorize")
	public ResponseEntity<String> getAuthorizeUrl() {
		String url = kakaoClientService.getAuthorizeUrl();
		return ResponseEntity.ok(url);
	}

	/**
	 * 카카오 로그인 혹은 회원가입
	 */
	@GetMapping("/kakao/log-in")
	public ResponseEntity<String> loginByKakao(@RequestParam String code) {
		String accessToken = kakaoAuthService.loginByKakao(code);
		return ResponseEntity.ok(accessToken);
	}
}
