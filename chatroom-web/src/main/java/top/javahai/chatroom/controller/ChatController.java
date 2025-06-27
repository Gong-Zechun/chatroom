package top.javahai.chatroom.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import java.util.Map;
import java.util.concurrent.TimeUnit;
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

  // 消息过期时间（单位：分钟）
  private static final long MESSAGE_TTL = 24 * 60; // 24小时
  private static final TimeUnit MESSAGE_TTL_UNIT = TimeUnit.MINUTES;

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
    if (StrUtil.isBlank(content) || !content.contains(GlobalConstants.SEPERATOR)) {
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

    saveGroupMessageWithTTL(messageV1);
    loginService.refreshToken(token);
    loginService.refreshUserInfoInRedis(token);
    return messageV1;
  }

  /**
   * 保存群聊消息（带TTL）
   */
  private void saveGroupMessageWithTTL(MessageV1 message) {
    // 1. 使用Sorted Set存储消息ID和时间戳（作为分数）
    String messageKey = "group:messages:ids";
    double score = message.getSendTime().getTime();
    redissonUtils.addToSortedSet(messageKey, message.getMessageId(), score);

    // 2. 单独存储每条消息内容（String结构）
    String messageDataKey = "group:message:" + message.getMessageId();
    redissonUtils.set(messageDataKey, message);
    redissonUtils.expire(messageDataKey, MESSAGE_TTL, MESSAGE_TTL_UNIT);

    // 3. 设置Sorted Set的TTL（可选，防止无限增长）
    redissonUtils.expire(messageKey, MESSAGE_TTL, MESSAGE_TTL_UNIT);
  }
}
