package com.example.groomingo.domain.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.groomingo.domain.auth.dto.CheckIsMemberRequest;
import com.example.groomingo.domain.auth.dto.Login;
import com.example.groomingo.domain.auth.dto.SignUpRequest;
import com.example.groomingo.domain.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	/**
	 * 로그인
	 */
	@PostMapping("/log-in")
	public ResponseEntity<String> login(@RequestBody Login.Request request) {
		String token = authService.login(request);
		return ResponseEntity.ok(token);
	}

	/**
	 * 회원가입 조회 여부 조회
	 */
	@PostMapping("/check-registration")
	public ResponseEntity<Boolean> checkIsMember(@RequestBody CheckIsMemberRequest request) {
		boolean response = authService.checkIsMember(request);
		return ResponseEntity.ok(response);
	}

	/**
	 * 유저 회원가입
	 */
	@PostMapping("/sign-up")
	public ResponseEntity<String> signUp(@RequestBody SignUpRequest request) {
		String email = authService.signUp(request);
		return ResponseEntity.ok(email);
	}
}
