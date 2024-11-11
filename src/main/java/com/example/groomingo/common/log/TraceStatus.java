package com.example.groomingo.common.log;

public record TraceStatus(TraceId traceId, Long startTimeMs, String message) {

}
