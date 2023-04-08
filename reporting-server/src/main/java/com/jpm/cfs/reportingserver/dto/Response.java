package com.jpm.cfs.reportingserver.dto;

import com.jpm.cfs.reportingserver.dto.error.ErrorEvent;

import java.util.Objects;

public class Response<T> {

    ErrorEvent errorResponse;
    T successResponse;

    public Response(ErrorEvent errorResponse) {
        this.errorResponse = errorResponse;
    }

    public Response(T successResponse) {
        this.successResponse = successResponse;
    }

    public boolean hasError() {
        return Objects.nonNull(errorResponse);
    }

    public ErrorEvent getErrorResponse() {
        return errorResponse;
    }

    public T getSuccessResponse() {
        return successResponse;
    }

    public static <T> Response<T> with(T t){
        return new Response<T>(t);
    }

    public static <T> Response<T> with(ErrorEvent errorEvent){
        return new Response<T>(errorEvent);
    }

    @Override
    public String toString() {
        return "Response{" +
                "errorResponse=" + errorResponse +
                ", successResponse=" + successResponse +
                '}';
    }
}
