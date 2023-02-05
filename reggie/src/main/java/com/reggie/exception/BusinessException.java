package com.reggie.exception;

import lombok.Data;

/**
 * 用户不规范输入异常
 */
@Data
public class BusinessException extends RuntimeException{


    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
