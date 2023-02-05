package com.reggie.common;

import com.reggie.exception.BusinessException;
import com.reggie.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 处理未知异常
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public R<String> doException(Exception ex){
        ex.printStackTrace();
        return R.error("系统繁忙，请稍后再试!");
    }

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public R<String> doBusinessException(Exception ex){
        ex.printStackTrace();
        return R.error(ex.getMessage());
    }

    /**
     * 处理系统异常
     * @param ex
     * @return
     */
    @ExceptionHandler(SystemException.class)
    public R<String> doSystemException(Exception ex){
        ex.printStackTrace();
        return R.error(ex.getMessage());
    }
}
