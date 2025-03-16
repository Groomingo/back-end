package com.example.groomingo.domain.manager.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.groomingo.common.exception.ExceptionState;
import com.example.groomingo.common.exception.detail.UserException;
import com.example.groomingo.domain.manager.domain.ManagerEntity;
import com.example.groomingo.domain.manager.dto.EnrollManager;
import com.example.groomingo.domain.manager.repository.ManagerRepository;
import com.example.groomingo.domain.user.domain.UserEntity;
import com.example.groomingo.domain.user.service.UserFindService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerService {

	private final UserFindService userFindService;
	private final ManagerRepository managerRepository;

	@Transactional
	public void enroll(EnrollManager.Request request, String email) {
		UserEntity userEntity = userFindService.getByEmail(email);
		validateIsAlreadyExist(userEntity);
		ManagerEntity managerEntity = createAndSaveManager(request);
		userEntity.enrollManager(managerEntity);
	}

	private static void validateIsAlreadyExist(UserEntity userEntity) {
		if (userEntity.getManagerEntity() != null) {
			throw new UserException(ExceptionState.MANAGER_ACCOUNT_IS_ALREADY_EXIST);
		}
	}

	private ManagerEntity createAndSaveManager(EnrollManager.Request request) {
		return managerRepository.save(ManagerEntity.builder()
			.nickname(request.nickname())
			.build());
	}
}
