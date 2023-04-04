package com.guojijian.reggie.commons.exception;

import com.guojijian.reggie.commons.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R exceptionHandler(SQLIntegrityConstraintViolationException e){
        log.error(e.getMessage());

        if(e.getMessage().contains("Duplicate entry")){
            String[] exceptionStr=e.getMessage().split(" ");
            return R.error("用户名"+exceptionStr[2]+"：已存在！");
        }

        return R.error("操作失败！");
    }

    @ExceptionHandler(AddException.class)
    public R addExceptionHandle(AddException e){
        log.error(e.getMessage());

        return R.error("操作失败！");
    }

    @ExceptionHandler(UpdateException.class)
    public R updateExceptionHandle(UpdateException e){
        log.error(e.getMessage());

        return R.error("操作失败！");
    }

    @ExceptionHandler(DeleteException.class)
    public R deleteExceptionHandle(DeleteException e){
        log.error(e.getMessage());

        return R.error("操作失败！");
    }
}
