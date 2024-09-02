package com.paymentology.reconsilation_service.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestTranslationException extends RuntimeException {
    private int code;

    public RequestTranslationException(int code, String message) {
        super(message);
        this.code = code;
    }


}
