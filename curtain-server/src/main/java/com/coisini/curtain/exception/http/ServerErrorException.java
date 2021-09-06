package com.coisini.curtain.exception.http;

import com.coisini.curtain.exception.basic.HttpException;

/**
 * @Description 业务异常
 * @author coisini
 * @date Aug 16, 2021
 * @Version 1.0
 */
public class ServerErrorException extends HttpException {
    public ServerErrorException(int code){
        this.code = code;
        this.httpStatusCode = 500;
    }
}

