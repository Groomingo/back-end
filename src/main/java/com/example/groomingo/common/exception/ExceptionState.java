package com.example.groomingo.common.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionState {

	/**
	 * 인증 관련 에러
	 */
	INVALID_TOKEN(403, HttpStatus.FORBIDDEN, "A0001", "Unauthorized"),
	ILLEGAL_ARGUMENT_TOKEN(403, HttpStatus.UNAUTHORIZED, "A0002", "Illegal argument token exception"),
	TOKEN_NOT_FOUND(401, HttpStatus.UNAUTHORIZED, "A0003", "Token is not found"),
	TOKEN_EXPIRED(401, HttpStatus.UNAUTHORIZED, "A0004", "Token is expired"),

	/**
	 * 유저 도메인 관련 에러
	 */
	USER_NOT_FOUND(400, HttpStatus.BAD_REQUEST, "U0001", "UserEntity is not found"),
	DUPLICATED_EMAIL(400, HttpStatus.BAD_REQUEST, "U0002", "email is already exist"),
	MANAGER_ACCOUNT_IS_ALREADY_EXIST(400, HttpStatus.BAD_REQUEST, "U0003", "Manager account is already exist"),
	;

	private final int value;
	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
