package com.coisini.curtain.common.util;

import com.coisini.curtain.vo.UnifyResponseVo;
import io.github.talelin.autoconfigure.bean.Code;
import io.github.talelin.autoconfigure.util.RequestUtil;
import com.coisini.curtain.vo.PageResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 响应结果生成工具
 */
@SuppressWarnings("unchecked")
@Slf4j
public class ResponseUtil {

    /**
     * 获得当前响应
     *
     * @return 响应
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static void setCurrentResponseHttpStatus(int httpStatus) {
        getResponse().setStatus(httpStatus);
    }

    public static <T> UnifyResponseVo<T> generateCreatedResponse(int code) {
        return (UnifyResponseVo<T>) UnifyResponseVo.builder()
                .message(Code.CREATED.getDescription())
                .code(code)
                .request(RequestUtil.getSimpleRequest())
                .build();
    }

    public static <T> UnifyResponseVo<T> generateCreatedResponse(int code, T data) {
        return (UnifyResponseVo<T>) UnifyResponseVo.builder()
                .message(data)
                .code(code)
                .request(RequestUtil.getSimpleRequest())
                .build();
    }

    public static <T> UnifyResponseVo<T> generateDeletedResponse(int code) {
        return (UnifyResponseVo<T>) UnifyResponseVo.builder()
                .message(Code.SUCCESS.getDescription())
                .code(code)
                .request(RequestUtil.getSimpleRequest())
                .build();
    }

    public static <T> UnifyResponseVo<T> generateDeletedResponse(int code, T data) {
        return (UnifyResponseVo<T>) UnifyResponseVo.builder()
                .message(data)
                .code(code)
                .request(RequestUtil.getSimpleRequest())
                .build();
    }

    public static <T> UnifyResponseVo<T> generateUpdatedResponse(int code) {
        return (UnifyResponseVo<T>) UnifyResponseVo.builder()
                .message(Code.SUCCESS.getDescription())
                .code(code)
                .request(RequestUtil.getSimpleRequest())
                .build();
    }

    public static <T> UnifyResponseVo<T> generateUpdatedResponse(int code, T data) {
        return (UnifyResponseVo<T>) UnifyResponseVo.builder()
                .message(data)
                .code(code)
                .request(RequestUtil.getSimpleRequest())
                .build();
    }

    public static <T> UnifyResponseVo<T> generateUnifyResponse(int code) {
        return (UnifyResponseVo<T>) UnifyResponseVo.builder()
                .code(code)
                .request(RequestUtil.getSimpleRequest())
                .build();
    }

    public static PageResponseVo generatePageResult(int total, List items, int page, int count) {
        return new PageResponseVo(total, items, page, count);
    }
}
