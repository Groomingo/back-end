package com.example.groomingo.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.groomingo.domain.user.domain.KakaoEntity;
import com.example.groomingo.domain.user.repository.KakaoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KakaoService {

	private final KakaoRepository kakaoRepository;

	public KakaoEntity save(KakaoEntity kakaoEntity) {
		return kakaoRepository.save(kakaoEntity);
	}
}
