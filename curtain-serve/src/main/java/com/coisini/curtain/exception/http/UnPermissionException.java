package com.coisini.curtain.exception.http;

/**
 * @Description Token无权限异常
 * @author coisini
 * @date Aug 9, 2021
 * @Version 1.0
 */
public class UnPermissionException extends RuntimeException{

    protected Integer code;
    protected Integer httpStatusCode;
    protected String msg;

    public UnPermissionException (int code) {
        this.code = code;
        this.httpStatusCode = 401;
        this.msg = "Token校验失败";
    }

    public UnPermissionException (String msg) {
        this.code = 401;
        this.httpStatusCode = 401;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getMsg() {
        return msg;
    }

}
