/*
 * Copyright (C) 2019 KQ GEO Technologies Co., Ltd.
 * All rights reserved.
 */
package top.javahai.chatroom.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置类
 *
 * <p> 更推荐实现 {@code WebMvcConfigurer} 接口, 覆写其 {@code addCorsMappings(CorsRegistry registry)}方法
 * <p> 这个应该是放网关就可以了吧. 浏览器端又不会直接访问内部组件?
 * @author TuHao
 */
@Configuration
public class CorsConfig {

  @Bean
  public CorsFilter corsFilter() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    final CorsConfiguration corsConfiguration = new CorsConfiguration();

    corsConfiguration.setAllowCredentials(true);
    //使用allowedOriginPatterns替代allowedOrigins
//    corsConfiguration.addAllowedOriginPattern("*");
    corsConfiguration.addAllowedOrigin("*");
    corsConfiguration.addAllowedHeader("*");
    corsConfiguration.addAllowedMethod("*");

    // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
    corsConfiguration.setMaxAge(3600L);

    source.registerCorsConfiguration("/**", corsConfiguration);
    return new CorsFilter(source);
  }
}
