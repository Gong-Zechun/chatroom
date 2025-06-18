package top.javahai.chatroom.newEntity;

import java.util.Date;
import lombok.Data;

/**
 * 群聊的消息实体
 *
 * @author Gong Zechun
 * @date 2025/6/17 - 10:48
 */
@Data
public class MessageV1 {

  private String messageId;
  private String username;
  private String message;
  private String headpic;
  private Date sendTime;
  private String sendTimeStr;
}
