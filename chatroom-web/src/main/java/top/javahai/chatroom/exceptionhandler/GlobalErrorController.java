/*
 * Copyright (C) 2019 KQ GEO Technologies Co., Ltd.
 * All rights reserved.
 */
package top.javahai.chatroom.exceptionhandler;

import com.alibaba.fastjson.JSON;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.javahai.chatroom.utils.KqRespEntity;

/**
 * 自定义错误页面
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@ConditionalOnProperty(value = "kq.errorpage.enabled", havingValue = "false", matchIfMissing = true)
public class GlobalErrorController implements ErrorController {

  @SneakyThrows
  @RequestMapping(value = "${server.error.path:/error}", method = {RequestMethod.GET, RequestMethod.POST})
  public void error(HttpServletRequest request, HttpServletResponse response) {
    HttpStatus status = getStatus(request);
    response.setStatus(status.value());
    KqRespEntity<?> entity = KqRespEntity.makeResp(status.value(), status.getReasonPhrase());
    response.getOutputStream().write(JSON.toJSONString(entity).getBytes("utf-8"));
  }

  private HttpStatus getStatus(HttpServletRequest req) {
    Integer statusCode = (Integer) req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    return statusCode == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.valueOf(statusCode);
  }

  @Override
  public String getErrorPath() {
    return null;
  }
}
