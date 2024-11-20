package com.example.groomingo.domain.auth.dto;

import lombok.Builder;

public class KakaoLogin {

	@Builder
	public record Request(String grant_type, String client_id, String redirect_uri, String code, String client_secret) {

		public static Request of(String apiKey, String redirectUri, String code) {
			return Request.builder()
				.grant_type("authorization_code")
				.client_id(apiKey)
				.redirect_uri(redirectUri)
				.code(code)
				.build();
		}
	}

	@Builder
	public record Response(String token_type, String access_token, String id_token, Integer expires_in,
   		String refresh_token, Integer refresh_token_expires_in) {
	}
}
