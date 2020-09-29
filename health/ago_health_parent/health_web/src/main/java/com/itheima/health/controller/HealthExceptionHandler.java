package com.itheima.health.controller;

import com.itheima.health.entity.Result;
import com.itheima.health.exception.HealthException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: Terrance
 * @Date: 2020-09-21 17:33
 *
 * Description: 全局异常处理，如果controller抛出异常，则会被这个类捕获
 */
@RestControllerAdvice
public class HealthExceptionHandler {
    /**
     * ExceptionHandler 这个注解(类.class) 专门捕获这样的异常 异常类型
     * try{
     *
     * }cathc(异常类型 e){ @ExceptionHandler(Exception.class)
     *     异常处理
     * }catch(ef ){
     *
     * }
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        e.printStackTrace();
        return new Result(false, "发生未知异常，请联系管理员");
    }

    /**
     * 一定要在接口上抛出异常
     * @param e
     * @return
     */
    @ExceptionHandler(HealthException.class)
    public Result handleHealthException(HealthException e){
        e.printStackTrace();
        return new Result(false, e.getMessage());
    }
}
