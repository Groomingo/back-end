package com.example.groomingo.domain.user.domain;

import com.example.groomingo.domain.auth.dto.KakaoInfo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "kakao")
public class KakaoEntity {

	@Id
	private Long id;
	private String email;
	private String nickname;

	public static KakaoEntity toEntity(KakaoInfo kakaoInfo) {
		return KakaoEntity.builder()
			.id(kakaoInfo.id())
			.email(kakaoInfo.kakaoAccount().email())
			.nickname(kakaoInfo.kakaoAccount().profile().nickname())
			.build();
	}
}
