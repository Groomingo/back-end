package com.example.groomingo.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.groomingo.domain.user.domain.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByEmail(String email);

	boolean existsByEmail(String email);

	@Query("SELECT u FROM UserEntity u where u.kakaoEntity.id = :kakaoId")
	Optional<UserEntity> findByKakaoId(@Param("kakaoId") Long kakaoId);
}
