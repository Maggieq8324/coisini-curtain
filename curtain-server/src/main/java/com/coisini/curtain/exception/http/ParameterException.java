package com.coisini.curtain.exception.http;

import com.coisini.curtain.exception.basic.HttpException;

/**
 * @Description 参数异常
 * @author coisini
 * @date Aug 18, 2021
 * @Version 1.0
 */
public class ParameterException extends HttpException {

    public ParameterException(int code){
        this.code = code;
        this.httpStatusCode = 400;
    }

}
