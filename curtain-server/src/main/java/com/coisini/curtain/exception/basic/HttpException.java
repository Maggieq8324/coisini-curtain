package com.coisini.curtain.exception.basic;

/**
 * @Description Http异常类
 * @author coisini
 * @date Aug 10, 2021
 * @Version 1.0
 */
public class HttpException extends RuntimeException{

    protected Integer code;
    protected Integer httpStatusCode = 500;

    public Integer getCode() {
        return code;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }
}
