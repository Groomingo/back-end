package com.example.groomingo.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.groomingo.domain.user.dto.SignUpRequest;
import com.example.groomingo.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	/**
	 * 유저 회원가입
	 */
	@PostMapping("/sign-up")
	public ResponseEntity<String> signUp(SignUpRequest request) {
		String email = userService.signUp(request);
		return ResponseEntity.ok(email);
	}
}
