package com.coisini.curtain.core.exception;

import com.coisini.curtain.config.ExceptionCodeConfiguration;
import com.coisini.curtain.core.common.UnifyMessage;
import com.coisini.curtain.exception.basic.HttpException;
import com.coisini.curtain.exception.http.UnPermissionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @Description 全局异常统一处理
 * @author coisini
 * @date Aug 9, 2021
 * @Version 1.0
 */
@ControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * @ControllerAdvice 需要标明一个具体处理类
     * @ExceptionHandler 异常处理器
     *
     * 异常分为两种
     * checkedException 可检测到异常，编译阶段进行处理，要求主动处理
     *                  应用场景：可处理异常如文件读取
     * runtimeException 运行时异常，不要求主动处理，
     *                  应用场景：不可处理异常如数据库读取
     *
     * 异常处理手段
     * 1、继续抛出
     * 2、日志记录
     */

    @Autowired
    private ExceptionCodeConfiguration exceptionCodeConfiguration;

    /**
     * 通用异常、未知异常处理
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyMessage handleException(HttpServletRequest request, Exception e) {
        String method = request.getMethod();
        String requestUrl = request.getRequestURI();
        System.out.println(e);
        e.printStackTrace();
        UnifyMessage message = new UnifyMessage(9999, "服务异常", method + " " + requestUrl);
        return message;
    }

    /**
     * http异常处理器
     * @param request
     * @param e
     */
    @ExceptionHandler(HttpException.class)
    public ResponseEntity handleHttpException (HttpServletRequest request, HttpException e) {
        String method = request.getMethod();
        String requestUrl = request.getRequestURI();
        System.out.println(e);
        e.printStackTrace();

        UnifyMessage message = new UnifyMessage(e.getCode(), exceptionCodeConfiguration.getMessage(e.getCode()), method + " " + requestUrl);
        // 设置返回类型
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpStatus status = HttpStatus.resolve(e.getHttpStatusCode());
        ResponseEntity<UnifyMessage> responseEntity = new ResponseEntity<>(message, headers, status);
        return responseEntity;
    }

    /**
     * 参数校验异常处理器
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public UnifyMessage handleBeanValidation(HttpServletRequest request, MethodArgumentNotValidException e) {
        String method = request.getMethod();
        String requestUrl = request.getRequestURI();
        System.out.println(e);
        e.printStackTrace();

        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String message = formatAllErrorMessages(errors);

        return new UnifyMessage(10001, message,method + " " + requestUrl);
    }

    /**
     * 自定义注解校验异常处理器
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code= HttpStatus.BAD_REQUEST)
    @ResponseBody
    public UnifyMessage handleConstraintException(HttpServletRequest req, ConstraintViolationException e){
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        String message = e.getMessage();

        e.printStackTrace();

        return new UnifyMessage(10001, message, method + " " + requestUrl);
    }

    /**
     * 异常消息拼接
     * @param errors
     * @return
     */
    private String formatAllErrorMessages(List<ObjectError> errors){
        StringBuffer errorMsg = new StringBuffer();
        errors.forEach(error ->
                errorMsg.append(error.getDefaultMessage()).append(";")
        );
        return errorMsg.toString();
    }

    /**
     * http异常处理器
     * @param request
     * @param e
     */
    @ExceptionHandler(UnPermissionException.class)
    public ResponseEntity handleUnPermissionException (HttpServletRequest request, UnPermissionException e) {
        String method = request.getMethod();
        String requestUrl = request.getRequestURI();
        System.out.println(e);
        e.printStackTrace();

        UnifyMessage message = new UnifyMessage(e.getCode(), e.getMsg(), method + " " + requestUrl);
        // 设置返回类型
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpStatus status = HttpStatus.resolve(e.getHttpStatusCode());
        ResponseEntity<UnifyMessage> responseEntity = new ResponseEntity<>(message, headers, status);
        return responseEntity;
    }

}
