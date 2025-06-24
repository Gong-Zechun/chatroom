package top.javahai.chatroom;

import java.util.TimeZone;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Hai
 * @date 2020/6/16 - 12:45
 */
@SpringBootApplication
@MapperScan("top.javahai.chatroom.mapper")
@EnableScheduling
public class ChatroomWebApplication {
  public static void main(String[] args) {
    // 在应用启动时设置时区为中国时区
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    SpringApplication.run(ChatroomWebApplication.class, args);
  }
}
