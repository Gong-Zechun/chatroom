package top.javahai.chatroom.entity;

import java.util.Date;
import lombok.Data;

@Data
public class UserVO extends User {

  private Date lastVisitTime;
  private String lastVisitTimeStr;
}
