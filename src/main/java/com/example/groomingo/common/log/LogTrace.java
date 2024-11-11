package com.example.groomingo.common.log;

import org.aspectj.lang.ProceedingJoinPoint;

public interface LogTrace {
	TraceStatus begin(ProceedingJoinPoint point);

	void end(TraceStatus status, String declareClass, Object result);

	void exception(TraceStatus status, Throwable e);
}