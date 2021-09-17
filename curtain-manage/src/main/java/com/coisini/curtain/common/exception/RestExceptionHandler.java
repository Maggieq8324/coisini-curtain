package com.coisini.curtain.common.exception;

import cn.hutool.core.util.StrUtil;
import com.coisini.curtain.common.configuration.CodeMessageConfiguration;
import com.coisini.curtain.vo.UnifyResponseVo;
import io.github.talelin.autoconfigure.bean.Code;
import io.github.talelin.autoconfigure.exception.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.github.talelin.autoconfigure.util.RequestUtil.getSimpleRequest;

/**
 * @author pedro@TaleLin
 * @author colorful@TaleLin
 * @author Juzi@TaleLin
 */
@Order
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @Value("${spring.servlet.multipart.max-file-size:20M}")
    private String maxFileSize;

    /**
     * HttpException
     */
    @ExceptionHandler({HttpException.class})
    public UnifyResponseVo processException(HttpException exception, HttpServletRequest request, HttpServletResponse response) {
        log.error("", exception);
        UnifyResponseVo unifyResponse = new UnifyResponseVo();
        unifyResponse.setRequest(getSimpleRequest(request));
        int code = exception.getCode();
        boolean messageOnly = exception.isMessageOnly();
        unifyResponse.setCode(code);
        response.setStatus(exception.getHttpCode());
        String errorMessage = CodeMessageConfiguration.getMessage(code);
        if (StrUtil.isBlank(errorMessage) || messageOnly) {
            unifyResponse.setMessage(exception.getMessage());
        } else {
            unifyResponse.setMessage(errorMessage);
        }
        return unifyResponse;
    }

    /**
     * ConstraintViolationException
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public UnifyResponseVo processException(ConstraintViolationException exception, HttpServletRequest request, HttpServletResponse response) {
        log.error("", exception);
        Map<String, Object> msg = new HashMap<>();
        exception.getConstraintViolations().forEach(constraintViolation -> {
            String template = constraintViolation.getMessage();
            String path = constraintViolation.getPropertyPath().toString();
            msg.put(StrUtil.toUnderlineCase(path), template);
        });
        UnifyResponseVo unifyResponse = new UnifyResponseVo();
        unifyResponse.setRequest(getSimpleRequest(request));
        unifyResponse.setMessage(msg);
        unifyResponse.setCode(Code.PARAMETER_ERROR.getCode());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return unifyResponse;
    }

    /**
     * NoHandlerFoundException
     */
    @ExceptionHandler({NoHandlerFoundException.class})
    public UnifyResponseVo processException(NoHandlerFoundException exception, HttpServletRequest request, HttpServletResponse response) {
        log.error("", exception);
        UnifyResponseVo unifyResponse = new UnifyResponseVo();
        unifyResponse.setRequest(getSimpleRequest(request));
        String message = CodeMessageConfiguration.getMessage(10025);
        if (StrUtil.isBlank(message)) {
            unifyResponse.setMessage(exception.getMessage());
        } else {
            unifyResponse.setMessage(message);
        }
        unifyResponse.setCode(Code.NOT_FOUND.getCode());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return unifyResponse;
    }

    /**
     * MissingServletRequestParameterException
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public UnifyResponseVo processException(MissingServletRequestParameterException exception, HttpServletRequest request, HttpServletResponse response) {
        log.error("", exception);
        UnifyResponseVo result = new UnifyResponseVo();
        result.setRequest(getSimpleRequest(request));

        String errorMessage = CodeMessageConfiguration.getMessage(10150);
        if (StrUtil.isBlank(errorMessage)) {
            result.setMessage(exception.getMessage());
        } else {
            result.setMessage(errorMessage + exception.getParameterName());
        }
        result.setCode(Code.PARAMETER_ERROR.getCode());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return result;
    }

    /**
     * MethodArgumentTypeMismatchException
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public UnifyResponseVo processException(MethodArgumentTypeMismatchException exception, HttpServletRequest request, HttpServletResponse response) {
        log.error("", exception);
        UnifyResponseVo result = new UnifyResponseVo();
        result.setRequest(getSimpleRequest(request));
        String errorMessage = CodeMessageConfiguration.getMessage(10160);
        if (StrUtil.isBlank(errorMessage)) {
            result.setMessage(exception.getMessage());
        } else {
            result.setMessage(exception.getValue() + errorMessage);
        }
        result.setCode(Code.PARAMETER_ERROR.getCode());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return result;
    }

    /**
     * ServletException
     */
    @ExceptionHandler({ServletException.class})
    public UnifyResponseVo processException(ServletException exception, HttpServletRequest request, HttpServletResponse response) {
        log.error("", exception);
        UnifyResponseVo result = new UnifyResponseVo();
        result.setRequest(getSimpleRequest(request));
        result.setMessage(exception.getMessage());
        result.setCode(Code.FAIL.getCode());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return result;
    }

    /**
     * MethodArgumentNotValidException
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public UnifyResponseVo processException(MethodArgumentNotValidException exception, HttpServletRequest request, HttpServletResponse response) {
        log.error("", exception);
        BindingResult bindingResult = exception.getBindingResult();
        List<ObjectError> errors = bindingResult.getAllErrors();
        Map<String, Object> msg = new HashMap<>();
        errors.forEach(error -> {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                msg.put(StrUtil.toUnderlineCase(fieldError.getField()), fieldError.getDefaultMessage());
            } else {
                msg.put(StrUtil.toUnderlineCase(error.getObjectName()), error.getDefaultMessage());
            }
        });
        UnifyResponseVo result = new UnifyResponseVo();
        result.setRequest(getSimpleRequest(request));
        result.setMessage(msg);
        result.setCode(Code.PARAMETER_ERROR.getCode());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return result;
    }

    /**
     * HttpMessageNotReadableException
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public UnifyResponseVo processException(HttpMessageNotReadableException exception, HttpServletRequest request, HttpServletResponse response) {
        log.error("", exception);
        UnifyResponseVo result = new UnifyResponseVo();
        result.setRequest(getSimpleRequest(request));
        String errorMessage = CodeMessageConfiguration.getMessage(10170);
        if (StrUtil.isBlank(errorMessage)) {
            result.setMessage(exception.getMessage());
        } else {
            result.setMessage(errorMessage);
        }
        result.setCode(Code.PARAMETER_ERROR.getCode());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return result;
    }

    /**
     * TypeMismatchException
     */
    @ExceptionHandler({TypeMismatchException.class})
    public UnifyResponseVo processException(TypeMismatchException exception, HttpServletRequest request, HttpServletResponse response) {
        log.error("", exception);
        UnifyResponseVo result = new UnifyResponseVo();
        result.setRequest(getSimpleRequest(request));
        result.setMessage(exception.getMessage());
        result.setCode(Code.PARAMETER_ERROR.getCode());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return result;
    }

    /**
     * MaxUploadSizeExceededException
     */
    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public UnifyResponseVo processException(MaxUploadSizeExceededException exception, HttpServletRequest request, HttpServletResponse response) {
        log.error("", exception);
        UnifyResponseVo result = new UnifyResponseVo();
        result.setRequest(getSimpleRequest(request));
        String errorMessage = CodeMessageConfiguration.getMessage(10180);
        if (StrUtil.isBlank(errorMessage)) {
            result.setMessage(exception.getMessage());
        } else {
            result.setMessage(errorMessage + maxFileSize);
        }
        result.setCode(Code.FILE_TOO_LARGE.getCode());
        response.setStatus(HttpStatus.PAYLOAD_TOO_LARGE.value());
        return result;
    }

    /**
     * Exception
     */
    @ExceptionHandler({Exception.class})
    public UnifyResponseVo processException(Exception exception, HttpServletRequest request, HttpServletResponse response) {
        log.error("", exception);
        UnifyResponseVo result = new UnifyResponseVo();
        result.setRequest(getSimpleRequest(request));
        result.setMessage(Code.INTERNAL_SERVER_ERROR.getZhDescription());
        result.setCode(Code.INTERNAL_SERVER_ERROR.getCode());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return result;
    }
}
