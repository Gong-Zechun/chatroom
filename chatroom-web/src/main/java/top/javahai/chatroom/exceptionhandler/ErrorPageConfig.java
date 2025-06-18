/*
 * Copyright (C) 2019 KQ GEO Technologies Co., Ltd.
 * All rights reserved.
 */
package top.javahai.chatroom.exceptionhandler;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * 错误页面配置类
 *
 * @author TuHao
 */
@Configuration
@ConditionalOnProperty(value = "kq.errorpage.enabled", havingValue = "true")
public class ErrorPageConfig implements ErrorPageRegistrar {

  @Override
  public void registerErrorPages(ErrorPageRegistry registry) {
    List<ErrorPage> errorPages = new ArrayList<>();
    errorPages.add(new ErrorPage(HttpStatus.NOT_FOUND, "/404.html"));
    errorPages.add(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html"));

    ErrorPage[] pages = new ErrorPage[errorPages.size()];
    registry.addErrorPages(errorPages.toArray(pages));
  }
}
