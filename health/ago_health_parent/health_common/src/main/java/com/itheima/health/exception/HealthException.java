package com.itheima.health.exception;

/**
 * @Author: Terrance
 * @Date: 2020-09-21 17:28
 *
 * 自定义异常：终止已知不符合业务逻辑的代码的继续执行
 */
public class HealthException extends RuntimeException {
    public HealthException(String message){
        super(message);
    }
}
