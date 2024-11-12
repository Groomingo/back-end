package com.example.groomingo.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.groomingo.common.exception.detail.TokenException;
import com.example.groomingo.common.exception.detail.UserException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserException.class)
	public ResponseEntity<ExceptionResponse> userExceptionHandler(HttpServletRequest request, UserException exception) {
		log.error("userExceptionHandler()" + exception);
		ExceptionResponse response = ExceptionResponse.of(request, exception);
		return ResponseEntity.status(response.getValue()).body(response);
	}

	@ExceptionHandler(TokenException.class)
	public ResponseEntity<ExceptionResponse> tokenExceptionHandler(HttpServletRequest request,
		TokenException exception) {
		log.error("tokenExceptionHandler()" + exception);
		ExceptionResponse response = ExceptionResponse.of(request, exception);
		return ResponseEntity.status(response.getValue()).body(response);
	}
}
