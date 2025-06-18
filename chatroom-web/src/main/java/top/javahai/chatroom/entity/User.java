package top.javahai.chatroom.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class User implements Serializable {

  private String username;

  private String token;

  private String headpic;

  private String clientIP;
}
