package com.coisini.curtain.vo;

import io.github.talelin.autoconfigure.bean.Code;
import com.coisini.curtain.common.util.ResponseUtil;
import org.springframework.http.HttpStatus;

/**
 * @author pedro@TaleLin
 */
public class UpdatedVo<T> extends UnifyResponseVo {

    public UpdatedVo() {
        super(Code.UPDATED.getCode());
        ResponseUtil.setCurrentResponseHttpStatus(HttpStatus.CREATED.value());
    }

    public UpdatedVo(int code) {
        super(code);
        ResponseUtil.setCurrentResponseHttpStatus(HttpStatus.CREATED.value());
    }

    public UpdatedVo(T message) {
        super(message);
        ResponseUtil.setCurrentResponseHttpStatus(HttpStatus.CREATED.value());
    }

    public UpdatedVo(int code, T message) {
        super(code, message);
        ResponseUtil.setCurrentResponseHttpStatus(HttpStatus.CREATED.value());
    }

}
