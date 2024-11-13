package com.example.groomingo.domain.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.groomingo.domain.auth.dto.Login;
import com.example.groomingo.domain.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	/**
	 * 로그인
	 */
	@PostMapping("/log-in")
	public ResponseEntity<String> logIn(@RequestBody Login.Request request) {
		String token = authService.login(request);
		return ResponseEntity.ok(token);
	}
}
