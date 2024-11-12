package com.example.groomingo.common.exception.detail;

import com.example.groomingo.common.exception.CustomException;
import com.example.groomingo.common.exception.ExceptionState;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException implements CustomException {

	private final ExceptionState state;

	public UserException(ExceptionState state) {
		super(state.getMessage());
		this.state = state;
	}

	public UserException(ExceptionState state, String message) {
		super(message);
		this.state = state;
	}
}
