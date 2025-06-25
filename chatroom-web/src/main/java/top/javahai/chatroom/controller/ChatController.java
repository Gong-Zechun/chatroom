package top.javahai.chatroom.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import top.javahai.chatroom.constant.GlobalConstants;
import top.javahai.chatroom.newEntity.MessageV1;
import top.javahai.chatroom.service.LoginService;
import top.javahai.chatroom.utils.JwtUtil;
import top.javahai.chatroom.utils.RedissonUtils;

@Controller
public class ChatController {

  @Autowired
  private RedissonUtils redissonUtils;
  @Autowired
  private LoginService loginService;

  /**
   * 群聊
   * <p>处理来自客户端的消息</p>
   *
   * @param content 客户端发送的消息
   * @return 发送回客户端的消息
   */
  @MessageMapping("/send")
  @SendTo("/topic/chat")
  public MessageV1 processMessageFromClient(String content) {
    // 将接收到的消息返回给所有订阅了 /topic/chat 的客户端。
    if (StrUtil.isBlank(content)) {
      return null;
    }

    if (!content.contains(GlobalConstants.SEPERATOR)) {
      return null;
    }

    String token = content.split(GlobalConstants.SEPERATOR)[0];
    String message = content.split(GlobalConstants.SEPERATOR)[1];

    Map<String, Object> map = JwtUtil.parseToken(token);

    MessageV1 messageV1 = new MessageV1();
    messageV1.setMessageId(IdUtil.fastSimpleUUID());
    messageV1.setUsername(map.get("username").toString());
    messageV1.setHeadpic(map.get("headpic").toString());
    messageV1.setMessage(message);
    messageV1.setSendTime(DateUtil.date());
    messageV1.setSendTimeStr(DateUtil.date().toString());

    saveGroupMsgs(messageV1);
    loginService.refreshToken(token);
    loginService.refreshUserInfoInRedis(token);
    return messageV1;
  }

  private void saveGroupMsgs(MessageV1 messageV1) {
    redissonUtils.putToHash("groupMsgs", messageV1.getMessageId(), messageV1);
  }
}
