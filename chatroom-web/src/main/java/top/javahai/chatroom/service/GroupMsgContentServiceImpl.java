package top.javahai.chatroom.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.javahai.chatroom.newEntity.MessageV1;
import top.javahai.chatroom.utils.RedissonUtils;

/**
 * (GroupMsgContent)表服务实现类
 *
 * @author makejava
 * @since 2020-06-17 10:51:13
 */
@Service
public class GroupMsgContentServiceImpl {

  @Autowired
  private RedissonUtils redissonUtils;

  public List<MessageV1> getRecentGroupMessages() {
    return getRecentGroupMessages(500);
  }

  public List<MessageV1> getRecentGroupMessages(int count) {
    String messageKey = "group:messages:ids";
    // 获取最新的count条消息ID（按分数从高到低）
    Collection<Object> messageIds = redissonUtils.getSortedSet(messageKey, 0, count - 1, false);

    List<MessageV1> messages = new ArrayList<>();
    for (Object id : messageIds) {
      String messageDataKey = "group:message:" + id;
      MessageV1 message = (MessageV1) redissonUtils.get(messageDataKey);
      if (message != null) {
        messages.add(message);
      }
    }
    return messages;
  }
}
