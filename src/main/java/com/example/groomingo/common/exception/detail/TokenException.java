package com.example.groomingo.common.exception.detail;

import com.example.groomingo.common.exception.CustomException;
import com.example.groomingo.common.exception.ExceptionState;

import lombok.Getter;

@Getter
public class TokenException extends RuntimeException implements CustomException {
	private final ExceptionState state;

	public TokenException(ExceptionState state) {
		super(state.getMessage());
		this.state = state;
	}

	public TokenException(ExceptionState state, String message) {
		super(message);
		this.state = state;
	}
}
