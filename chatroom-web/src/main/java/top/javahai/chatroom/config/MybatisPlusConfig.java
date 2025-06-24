package top.javahai.chatroom.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import java.time.LocalDateTime;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

  @Bean
  public ConfigurationCustomizer configurationCustomizer() {
    return configuration -> {
      configuration.getTypeHandlerRegistry()
        .register(LocalDateTime.class, LocalDateTimeTypeHandler.class);
    };
  }
}
