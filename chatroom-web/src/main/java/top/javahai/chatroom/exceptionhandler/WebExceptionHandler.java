/*
 * Copyright (C) 2019 KQ GEO Technologies Co., Ltd.
 * All rights reserved.
 */
package top.javahai.chatroom.exceptionhandler;

import cn.hutool.core.util.StrUtil;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerMapping;
import top.javahai.chatroom.utils.KqRespCode;
import top.javahai.chatroom.utils.KqRespEntity;
import top.javahai.chatroom.utils.TranslateUtil;
import top.javahai.chatroom.exception.KqException;

/**
 * 全局异常处理
 *
 * @author TuHao
 */
@RestControllerAdvice
@Slf4j
public class WebExceptionHandler {

  private void updateMediaType(HttpServletRequest request) {
    request.setAttribute(
        HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE,
        Collections.singleton(MediaType.APPLICATION_JSON)
    );
  }

  private void setStatus(int statusCode, HttpServletResponse response) {
    response.setStatus(statusCode);
  }

  /** 通用异常 */

  /**
   * 缺少url参数
   *
   * @param e
   * @return
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public KqRespEntity<String> handleMissingParameterException(
      HttpServletRequest request, HttpServletResponse response, MissingServletRequestParameterException e
  ) {
    log.error(e.getMessage(), e);
    setStatus(KqRespCode.BAD_REQUEST.getCode(), response);
    updateMediaType(request);
    return KqRespEntity.makeResp(
        KqRespCode.BAD_REQUEST,
        TranslateUtil.getMsg("param.missing", new String[]{e.getParameterName(), e.getParameterType()})
    );
  }

  /**
   * 缺少body参数
   *
   * @param e
   * @return
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public KqRespEntity<String> handleMessageNotReadableException(
      HttpServletRequest request, HttpServletResponse response, HttpMessageNotReadableException e
  ) {
    log.error(e.getMessage(), e);
    setStatus(KqRespCode.BAD_REQUEST.getCode(), response);
    updateMediaType(request);
    return KqRespEntity.makeResp(KqRespCode.BAD_REQUEST, e.getMessage());
  }

  /**
   * 使用@Validated验证@RequestBody失败后抛出的异常
   *
   * @param e
   * @return
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public KqRespEntity<String> handleMethodArgumentNotValidException(
      HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException e
  ) {
    String msg = getFieldErrorsMessages(e.getBindingResult().getFieldErrors());
    log.error(msg, e);
    setStatus(KqRespCode.BAD_REQUEST.getCode(), response);
    updateMediaType(request);
    return KqRespEntity.makeResp(KqRespCode.BAD_REQUEST, msg);
  }

  /**
   * 使用@Valid验证请求实体对象失败后抛出的异常
   *
   * @param e
   * @return
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BindException.class)
  public KqRespEntity<String> handleBindException(
      HttpServletRequest request,
      HttpServletResponse response, BindException e
  ) {
    String msg = getFieldErrorsMessages(e.getFieldErrors());
    log.error(msg, e);
    setStatus(KqRespCode.BAD_REQUEST.getCode(), response);
    updateMediaType(request);
    return KqRespEntity.makeResp(KqRespCode.BAD_REQUEST, msg);
  }

  /**
   * 使用不支持的方法时抛出的异常
   *
   * @param e
   * @return
   */
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public KqRespEntity<String> handleMethodNotSupportedException(
      HttpServletRequest request, HttpServletResponse response, HttpRequestMethodNotSupportedException e
  ) {
    String msg = TranslateUtil.getMsg("http.method.notSupported", new String[]{e.getMethod()});
    log.error(msg);
    setStatus(KqRespCode.BAD_REQUEST.getCode(), response);
    updateMediaType(request);
    return KqRespEntity.makeResp(KqRespCode.BAD_REQUEST, msg);
  }

  /**
   * 调用者的Content-Type属性设置错误
   *
   * @param e
   * @return
   */
  @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public KqRespEntity<String> handleMediaTypeNotSupportedException(
      HttpServletRequest request, HttpServletResponse response, HttpMediaTypeNotSupportedException e
  ) {
    String msg = String.format("[%s]%s", e.getContentType(), e.getMessage());
    log.error(msg, e);
    setStatus(KqRespCode.BAD_REQUEST.getCode(), response);
    updateMediaType(request);
    return KqRespEntity.makeResp(KqRespCode.BAD_REQUEST, msg);
  }

  /**
   * 一般业务异常
   *
   * @param e
   * @return
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(KqException.class)
  public KqRespEntity<String> handleKqException(
      HttpServletRequest request,
      HttpServletResponse response, KqException e
  ) {
    String msg = e.getMessage();
    log.error(msg, e);
    setStatus(KqRespCode.BAD_REQUEST.getCode(), response);
    updateMediaType(request);
    return KqRespEntity.makeResp(e.getCode(), msg);
  }

  /** 其它异常 */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = Exception.class)
  public KqRespEntity<String> handleException(
      HttpServletRequest request,
      HttpServletResponse response, Exception e
  ) {
    // 获取异常出错位置
    StackTraceElement el = null;
    StackTraceElement[] elements = e.getStackTrace();
    for (StackTraceElement element : elements) {
      String className = element.getClassName();
      boolean isKanqClass = className.contains("cn.com.kanq") || className.contains("cn.kanq");
      if (isKanqClass && !className.contains("OptionsFilter")) {
        el = element;
        break;
      }
    }

    String simpleMsg = e.getMessage();
    if (StrUtil.isBlank(simpleMsg) && e instanceof RuntimeException) {
      simpleMsg = e.toString();
    }

    Throwable throwable = e.getCause();
    if (StrUtil.isBlank(simpleMsg) && throwable != null) {
      simpleMsg = throwable.getMessage();
    }

    // todo gzc 这里会丢失报错详情，先注释掉，后续优化
    // if (StringUtils.isNotBlank(simpleMsg)) {
    // simpleMsg = e.getClass().getName();
    // }

    String msg = simpleMsg;
    if (el != null) {
      msg = String.format("[%s:%d]%s", el.getClassName(), el.getLineNumber(), simpleMsg);
    }

    log.error(String.format("%s %s", request.getRequestURL().toString(), msg), e);
    setStatus(KqRespCode.BAD_REQUEST.getCode(), response);
    updateMediaType(request);
    return KqRespEntity.makeResp(KqRespCode.EXPECTATION_FAILED, msg);
  }

  private String getFieldErrorsMessages(List<FieldError> fieldErrors) {
    return fieldErrors.stream()//
        .map(err -> String.format("%s:%s", err.getField(), err.getDefaultMessage()))//
        .collect(Collectors.joining(";"));
  }
}
