package com.example.groomingo.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.groomingo.domain.user.domain.KakaoEntity;

public interface KakaoRepository extends JpaRepository<KakaoEntity, Long> {
}
