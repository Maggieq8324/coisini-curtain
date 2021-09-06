package com.coisini.curtain.core.common;

import com.coisini.curtain.exception.resp.CreateSuccess;

/**
 * @Description 统一消息返回
 * @author coisini
 * @date Aug 9, 2021
 * @Version 1.0
 */
public class UnifyMessage {

    private int code;
    private String message;
    private String requestUrl;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public UnifyMessage(int code, String message, String requestUrl) {
        this.code = code;
        this.message = message;
        this.requestUrl = requestUrl;
    }

    public static void createSuccess(int code) {
        throw new CreateSuccess(code);
    }

}
