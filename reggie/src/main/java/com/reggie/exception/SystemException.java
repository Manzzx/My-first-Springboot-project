package com.reggie.exception;

import lombok.Data;

/**
 * 可预知系统异常
 */
@Data
public class SystemException extends RuntimeException{


    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
