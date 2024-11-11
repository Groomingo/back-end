package com.example.groomingo.common.log;

import java.util.Map;
import java.util.stream.Collectors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ThreadLocalTrace implements LogTrace {

	private static final String START_PREFIX = "-->";
	private static final String COMPLETE_PREFIX = "<--";
	private static final String EX_PREFIX = "<X-";

	private final ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>();

	@Override
	public TraceStatus begin(ProceedingJoinPoint point) {
		syncTraceId();
		TraceId traceId = traceIdHolder.get();

		MDC.put("trace_id", traceId.getId());

		String declareClass = point.getSignature().toShortString();
		logRequestController(point, declareClass);

		Long startTimeMs = System.currentTimeMillis();

		log.info("{}{}",
			addSpace(START_PREFIX, traceId.getLevel()),
			declareClass);

		return new TraceStatus(traceId, startTimeMs, declareClass);
	}

	private void syncTraceId() {
		TraceId traceId = traceIdHolder.get();
		if (traceId == null) {
			traceIdHolder.set(new TraceId());
		} else {
			traceIdHolder.set(traceId.createNextId());
		}
	}

	@Override
	public void end(TraceStatus status, String declareClass, Object result) {
		logResponse(declareClass, result);
		complete(status, null);
	}

	@Override
	public void exception(TraceStatus status, Throwable e) {
		complete(status, e);
	}

	private void complete(TraceStatus status, Throwable e) {
		Long stopTimeMs = System.currentTimeMillis();
		long resultTimeMs = stopTimeMs - status.startTimeMs();
		TraceId traceId = status.traceId();

		if (e == null) {
			log.info("{}{} time={}ms",
				addSpace(COMPLETE_PREFIX, traceId.getLevel()),
				status.message(),
				resultTimeMs);
		} else {
			log.error("{}{} time={}ms ex={}",
				addSpace(EX_PREFIX, traceId.getLevel()),
				status.message(),
				resultTimeMs,
				e.toString());
		}
		releaseTraceId();
	}

	private void releaseTraceId() {
		TraceId traceId = traceIdHolder.get();
		if (traceId.isFirstLevel()) {
			traceIdHolder.remove();
			MDC.clear();
		} else {
			traceIdHolder.set(traceId.createPreviousId());
		}
	}

	private static String addSpace(String prefix, int level) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < level; i++) {
			sb.append(
				(i == level - 1)
					? "|" + prefix
					: "|   "
			);
		}
		return sb.toString();
	}

	private void logRequestController(ProceedingJoinPoint point, String declareClass) {
		if (declareClass.contains("Controller")) {
			HttpServletRequest request =
				((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();

			Map<String, String[]> paramMap = request.getParameterMap();
			String params = "";
			if (paramMap.isEmpty()) {
				params = " [" + paramMapToString(paramMap) + "]";
			}

			log.debug("{} Request -> {} {}, params = {}, body = {}", declareClass, request.getMethod(),
				request.getRequestURI(), params, point.getArgs());
		}
	}

	private void logResponse(String declareClass, Object result) {
		if (declareClass.contains("Controller")) {
			log.debug("{} Response -> {} ", declareClass, result);
		}
	}

	private String paramMapToString(Map<String, String[]> paramMap) {
		return paramMap.entrySet().stream()
			.map(entry -> String.format("%s -> (%s)",
				entry.getKey(), String.join(",", entry.getValue())))
			.collect(Collectors.joining(", "));
	}
}

