package com.paymentology.reconsilation_service.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FileParsingException extends RuntimeException {
   private int code;

    public FileParsingException(int code, String message, Throwable t) {
        super(message, t);
        this.code = code;
    }

}
