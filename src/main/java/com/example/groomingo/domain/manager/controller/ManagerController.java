package com.example.groomingo.domain.manager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.groomingo.domain.auth.SecurityUtil;
import com.example.groomingo.domain.manager.dto.EnrollManager;
import com.example.groomingo.domain.manager.service.ManagerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {

	private final ManagerService managerService;

	/**
	 * 매니져 계정으로 전환 요청
	 */
	@PostMapping
	public ResponseEntity<Void> enrollManager(@RequestBody EnrollManager.Request request) {
		String email = SecurityUtil.getEmailFromToken();
		managerService.enroll(request, email);
		return ResponseEntity.ok().build();
	}
}
