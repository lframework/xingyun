package com.lframework.xingyun.template.core.components;

import cn.dev33.satoken.exception.NotPermissionException;
import com.lframework.starter.common.exceptions.BaseException;
import com.lframework.starter.common.exceptions.ClientException;
import com.lframework.starter.common.exceptions.SysException;
import com.lframework.starter.common.exceptions.impl.AccessDeniedException;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.components.validation.TypeMismatch;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.resp.Response;
import com.lframework.starter.web.resp.ResponseErrorBuilder;
import com.lframework.starter.web.utils.ResponseUtil;
import java.util.Map;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 异常处理器
 *
 * @author zmj
 */
@Slf4j
@RestControllerAdvice
public class WebExceptionHandler {

  /**
   * 处理系统异常
   *
   * @param e
   * @return
   */
  @ExceptionHandler(ClientException.class)
  public Response handle(ClientException e, HandlerMethod method) {

    this.logException(e, method);

    this.setResponseCode(e);

    return InvokeResultBuilder.fail(e);
  }

  /**
   * 处理系统异常
   *
   * @param e
   * @return
   */
  @ExceptionHandler(SysException.class)
  public Response handle(SysException e, HandlerMethod method) {

    this.logException(e, method);

    BaseException ex = new DefaultSysException();
    this.setResponseCode(ex);

    return getBuilder(method.getBean()).fail(ex);
  }

  /**
   * 处理传入参数类型转换错误异常
   *
   * @param e
   * @param method
   * @return
   */
  @ExceptionHandler(UnexpectedTypeException.class)
  public Response handle(UnexpectedTypeException e, HandlerMethod method) {

    this.logException(e, method);

    BaseException ex = new InputErrorException();

    this.setResponseCode(ex);

    return getBuilder(method.getBean()).fail(ex);
  }

  @ExceptionHandler(BindException.class)
  public Response handle(BindException e, HandlerMethod method) {

    this.logException(e, method);

    InputErrorException exception = null;

    if (e.getErrorCount() > 0) {
      ObjectError objectError = e.getAllErrors().get(0);

      if (objectError instanceof FieldError && "typeMismatch".equals(objectError.getCode())) {
        String fieldName = ((FieldError) objectError).getField();

        Class targetClazz = e.getBindingResult().getTarget().getClass();

        TypeMismatch typeMismatch = null;
        try {
          typeMismatch = targetClazz.getDeclaredField(fieldName).getAnnotation(TypeMismatch.class);
        } catch (NoSuchFieldException exp) {
          throw new DefaultSysException(exp.getMessage());
        }
        if (typeMismatch != null) {
          exception = new InputErrorException(typeMismatch.message());
        }
      }
    }
    if (exception == null) {
      for (ObjectError error : e.getAllErrors()) {
        exception = new InputErrorException(error.getDefaultMessage());
        break;
      }

      if (exception == null) {
        exception = new InputErrorException();
      }
    }

    this.setResponseCode(exception);

    return getBuilder(method.getBean()).fail(exception);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Response methodArgumentNotValidException(MethodArgumentNotValidException e,
      HandlerMethod method) {

    this.logException(e, method);
    InputErrorException exception = null;
    for (ObjectError error : e.getBindingResult().getAllErrors()) {
      exception = new InputErrorException(error.getDefaultMessage());
      break;
    }

    if (exception == null) {
      throw new InputErrorException();
    }

    this.setResponseCode(exception);

    return getBuilder(method.getBean()).fail(exception);
  }

  /**
   * 处理未通过校验异常
   *
   * @param e
   * @param method
   * @return
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public Response handle(ConstraintViolationException e, HandlerMethod method) {

    this.logException(e, method);

    InputErrorException exception = null;
    for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
      exception = new InputErrorException(constraintViolation.getMessage());
      break;
    }

    if (exception == null) {
      throw new InputErrorException();
    }

    this.setResponseCode(exception);

    return getBuilder(method.getBean()).fail(exception);
  }

  /**
   * 处理由于传入参数类型不匹配导致的异常
   *
   * @param e
   * @param method
   * @return
   */
  @ExceptionHandler(TypeMismatchException.class)
  public Response methodArgumentTypeMismatchException(TypeMismatchException e,
      HandlerMethod method) {

    this.logException(e, method);

    MethodParameter methodParameter = null;
    if (e instanceof MethodArgumentConversionNotSupportedException) {
      methodParameter = ((MethodArgumentConversionNotSupportedException) e).getParameter();
    } else if (e instanceof MethodArgumentTypeMismatchException) {
      methodParameter = ((MethodArgumentTypeMismatchException) e).getParameter();
    }

    BaseException ex = null;
    if (methodParameter != null) {
      TypeMismatch typeMismatch = methodParameter.getMethod()
          .getParameters()[methodParameter.getParameterIndex()].getAnnotation(TypeMismatch.class);
      if (typeMismatch != null) {
        ex = new InputErrorException(typeMismatch.message());
      }
    }

    if (ex == null) {
      ex = new InputErrorException();
    }

    this.setResponseCode(ex);

    return getBuilder(method.getBean()).fail(ex);
  }

  /**
   * 处理由于传入参数类型不匹配导致的异常
   *
   * @param e
   * @param method
   * @return
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public Response invalidFormatException(HttpMessageNotReadableException e, HandlerMethod method) {

    this.logException(e, method);

    BaseException ex = new InputErrorException();
    this.setResponseCode(ex);

    return getBuilder(method.getBean()).fail(ex);
  }

  /**
   * 处理由于传入方式错误导致的异常
   *
   * @param e
   * @param method
   * @return
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public Response httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e,
      HandlerMethod method) {

    this.logException(e, method);

    BaseException ex = new DefaultSysException();

    this.setResponseCode(ex);

    return getBuilder(method.getBean()).fail();
  }

  @ExceptionHandler(FileUploadException.class)
  public Response fileUploadExceptionHandler(FileUploadException e, HandlerMethod method) {

    this.logException(e, method);

    BaseException ex = new DefaultClientException("文件上传失败！");

    this.setResponseCode(ex);

    return getBuilder(method.getBean()).fail(ex);
  }

  /**
   * 处理Exception
   *
   * @param e
   * @param method
   * @return
   */
  @ExceptionHandler(Exception.class)
  public Response exceptionHandler(Exception e, HandlerMethod method) {

    this.logException(e, method);

    BaseException ex = new DefaultSysException();

    this.setResponseCode(ex);

    return getBuilder(method.getBean()).fail(ex);
  }

  /**
   * 处理Throwable
   *
   * @param e
   * @param method
   * @return
   */
  @ExceptionHandler(Throwable.class)
  public Response throwableHandle(Throwable e, HandlerMethod method) {

    this.logException(e, method);

    BaseException ex = new InputErrorException();

    this.setResponseCode(ex);

    return getBuilder(method.getBean()).fail(ex);
  }

  /**
   * 处理无权限异常
   *
   * @param e
   * @return
   */
  @ExceptionHandler(AccessDeniedException.class)
  public Response handle(AccessDeniedException e, HandlerMethod method) {

    this.logException(e, method);

    BaseException ex = new AccessDeniedException();
    this.setResponseCode(ex);

    return this.getBuilder(method.getBean()).fail(ex);
  }

  @ExceptionHandler(NotPermissionException.class)
  public Response notPermissionException(NotPermissionException e, HandlerMethod method) {

    this.logException(e, method);

    BaseException ex = new AccessDeniedException();

    this.setResponseCode(ex);

    return this.getBuilder(method.getBean()).fail(ex);
  }

  protected void logException(Throwable e, HandlerMethod method) {

    if (e instanceof ClientException) {
      if (log.isDebugEnabled()) {
        String className = method.getBeanType().getName();
        String methodName = method.getMethod().getName();
        log.debug("className={}, methodName={}, 有异常产生", className, methodName, e);
      }
    } else {
      String className = method.getBeanType().getName();
      String methodName = method.getMethod().getName();
      log.error("className={}, methodName={}, 有异常产生", className, methodName, e);
    }
  }

  protected void setResponseCode(BaseException e) {

    ResponseUtil.getResponse().setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
  }

  protected ResponseErrorBuilder getBuilder(Object bean) {

    Map<String, ResponseErrorBuilder> builders = ApplicationUtil.getBeansOfType(
        ResponseErrorBuilder.class);
    ResponseErrorBuilder builder = null;
    for (ResponseErrorBuilder value : builders.values()) {
      if (value.isDefault()) {
        builder = value;
        break;
      }
    }

    for (ResponseErrorBuilder value : builders.values()) {
      if (value.isMatch(bean)) {
        builder = value;
        break;
      }
    }

    return builder;
  }
}
