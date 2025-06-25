package top.javahai.chatroom.service;

import cn.hutool.core.map.MapUtil;
import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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

  public List<MessageV1> getLimitGroupMsgs() {
    Map<String, Object> map = redissonUtils.getHash("groupMsgs");
    if (MapUtil.isEmpty(map)) {
      return Lists.newArrayList();
    }

    List<MessageV1> messageV1s = Lists.newArrayList();
    for (String messageId : map.keySet()) {
      messageV1s.add((MessageV1) map.get(messageId));
    }
    int size = messageV1s.size() > 500 ? 500 : messageV1s.size();
    messageV1s.sort(Comparator.comparing(MessageV1::getSendTime));
    return messageV1s.subList(0, size);
  }
}
