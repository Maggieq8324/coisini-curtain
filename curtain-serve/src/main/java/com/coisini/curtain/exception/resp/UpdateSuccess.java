package com.coisini.curtain.exception.resp;

import com.coisini.curtain.exception.basic.HttpException;

/**
 * @Description 修改成功
 * @author coisini
 * @date Aug 21, 2021
 * @Version 1.0
 */
public class UpdateSuccess extends HttpException {

    public UpdateSuccess(int code){
        this.httpStatusCode = 200;
        this.code = code;
    }

}
