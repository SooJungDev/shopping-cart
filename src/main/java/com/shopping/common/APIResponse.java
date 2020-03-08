package com.shopping.common;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class APIResponse implements Serializable {

    private int status;

    private String message;

    private String errorCode;

    private Object data;

    private List<?> list;

    public APIResponse() {
        this(HttpStatus.OK.value(), "success");
    }

    public APIResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.errorCode = "0000";
    }

    public APIResponse(int status, String message, String errorCode) {
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
    }
}
