/*
 * Copyright (C) 2019 KQ GEO Technologies Co., Ltd.
 * All rights reserved.
 */
package top.javahai.chatroom.exception;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import top.javahai.chatroom.utils.KqRespCode;

/**
 * 自定义异常类
 *
 * @author: Gong Zechun
 * @date: 2020/3/10 15:20
 */
@Getter
@SuppressWarnings("serial")
public class KqException extends RuntimeException {

  private final int code;
  private final String message;

  public KqException(KqRespCode exceptionCode) {
    super();
    this.code = exceptionCode.getCode();
    this.message = exceptionCode.getMsg();
  }

  public KqException(KqRespCode exceptionCode, Object[] params) {
    super();
    this.code = exceptionCode.getCode();
    this.message = exceptionCode.getMsg(params);
  }

  public KqException(KqRespCode exceptionCode, String... params) {
    super();
    this.code = exceptionCode.getCode();
    this.message = exceptionCode.getMsg(params);
  }

  public KqException(int code, String message) {
    super();
    this.code = code;
    this.message = message;
  }

  // ==================================
  public KqException(KqRespCode exceptionCode, Throwable cause) {
    super(cause);
    this.code = exceptionCode.getCode();
    this.message = exceptionCode.getMsg();
  }

  public KqException(KqRespCode exceptionCode, Object[] params, Throwable cause) {
    super(cause);
    this.code = exceptionCode.getCode();
    this.message = exceptionCode.getMsg(params);
  }

  public KqException(Throwable cause, KqRespCode exceptionCode, String... params) {
    super(cause);
    this.code = exceptionCode.getCode();
    this.message = exceptionCode.getMsg(params);
  }

  public KqException(int code, String message, Throwable cause) {
    super(cause);
    this.code = code;
    this.message = message;
  }

  @Override
  public String getMessage() {
    if (StrUtil.isNotBlank(message)) {
      return message;
    }

    KqRespCode[] kqRespCodeArr = KqRespCode.values();
    for (KqRespCode kqRespCode : kqRespCodeArr) {
      if (code == kqRespCode.getCode()) {
        return kqRespCode.getMsg();
      }
    }
    return null;
  }

  public KqException notFillInStackTrace() {
    return fillInStackTrace(false);
  }

  // 此方法可以设置是否填充堆栈跟踪信息
  public KqException fillInStackTrace(boolean shouldFill) {
    if (shouldFill) {
      this.fillInStackTrace(); // 填充堆栈跟踪信息
    } else {
      this.setStackTrace(new StackTraceElement[0]); // 不填充堆栈跟踪信息
    }
    return this;
  }
}
