package com.example.groomingo.domain.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.groomingo.domain.auth.dto.KakaoInfo;
import com.example.groomingo.domain.auth.dto.KakaoLogin;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KakaoClientService {

	private final WebClient.Builder builder;
	@Value("${kakao.api-key}")
	private String KAKAO_API_KEY;
	@Value("${kakao.redirect-url}")
	private String REDIRECT_URL;

	public String getAuthorizeUrl() {
		WebClient webClient = builder.build();
		String uri = "https://kauth.kakao.com/oauth/authorize"
			+ "?client_id=" + KAKAO_API_KEY
			+ "&redirect_uri=" + REDIRECT_URL
			+ "&response_type=code";

		return webClient.get()
			.uri(uri)
			.retrieve()
			.bodyToMono(String.class)
			.block();

	}

	public KakaoLogin.Response authorize(String code) {
		WebClient webClient = builder.build();
		String uri = "https://kauth.kakao.com/oauth/token";

		KakaoLogin.Request request = KakaoLogin.Request.of(KAKAO_API_KEY, REDIRECT_URL, code);

		return webClient.post()
			.uri(uri)
			.header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
			.bodyValue(request)
			.retrieve()
			.bodyToMono(KakaoLogin.Response.class)
			.block();
	}

	public KakaoInfo getUserInfo(String accessToken) {
		WebClient webClient = builder.build();
		String uri = "https://kapi.kakao.com/v2/user/me";

		return webClient.get()
			.uri(uri)
			.header("Authorization", "Bearer " + accessToken,
				HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
			.retrieve()
			.bodyToMono(KakaoInfo.class)
			.block();
	}
}
