package com.shopping.common.configs.exception;

import lombok.Getter;

public class APIErrorHandleException extends RuntimeException {

    @Getter
    private String errorCode;

    public APIErrorHandleException(String message) {
        super(message);
    }

    public APIErrorHandleException(String message, Throwable throwable) {
        super(message, throwable);
    }

    private APIErrorHandleException(Throwable throwable) {
        super(throwable);
    }

}
