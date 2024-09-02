package com.paymentology.reconsilation_service.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseParsingException extends RuntimeException {

    private int code;

    public ResponseParsingException(int code, String message, Throwable e) {
        super(message, e);
        this.code = code;

    }
}
