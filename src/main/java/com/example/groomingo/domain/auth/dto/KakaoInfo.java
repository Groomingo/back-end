package com.example.groomingo.domain.auth.dto;

public record KakaoInfo(Long id, KakaoAccount kakaoAccount) {

	public record KakaoAccount(Profile profile, String email) {
	}

	public record Profile(String nickname) {
	}
}
