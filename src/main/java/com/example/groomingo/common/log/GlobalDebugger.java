package com.example.groomingo.common.log;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class GlobalDebugger {

	private final LogTrace logTrace;

	@Pointcut("execution(* com.example.groomingo.domain..*.*(..))"
		+ " && !@annotation(NotDebugMethod)")
	private void pointcut() {
	}

	@Around("pointcut()")
	public Object trace(ProceedingJoinPoint point) throws Throwable {
		TraceStatus status = null;
		try {
			status = logTrace.begin(point);
			Object result = point.proceed();
			logTrace.end(status, point.getSignature().toShortString(), result);
			return result;
		} catch (Throwable e) {
			logTrace.exception(status, e);
			throw e;
		}
	}
}
