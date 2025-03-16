package com.example.groomingo.domain.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.groomingo.domain.manager.domain.ManagerEntity;

public interface ManagerRepository extends JpaRepository<ManagerEntity, Long> {
}
