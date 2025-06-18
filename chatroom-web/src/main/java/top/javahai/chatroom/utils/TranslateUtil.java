/*
 * Copyright (C) 2019 KQ GEO Technologies Co., Ltd.
 * All rights reserved.
 */
package top.javahai.chatroom.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 国际化工具类
 *
 * @author TuHao
 * 
 * @date 2020/4/28
 */
public class TranslateUtil {

  private static final MessageSource messageSource = messageSource();

  public static MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:messages");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

  public static String getMsg(String msgCode) {
    try {
      return messageSource.getMessage(msgCode, null, LocaleContextHolder.getLocale());
    } catch (Exception e) {
      return msgCode;
    }
  }

  public static String getMsg(String msgCode, Object[] params) {
    return messageSource.getMessage(msgCode, params, LocaleContextHolder.getLocale());
  }
}
