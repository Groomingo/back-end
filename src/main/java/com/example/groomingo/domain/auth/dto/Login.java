package com.example.groomingo.domain.auth.dto;

public class Login {

	public record Request(String username, String password) {
	}
}
