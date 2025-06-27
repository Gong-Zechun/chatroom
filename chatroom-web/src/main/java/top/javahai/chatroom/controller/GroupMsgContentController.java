package top.javahai.chatroom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.javahai.chatroom.service.GroupMsgContentServiceImpl;
import top.javahai.chatroom.utils.KqRespEntity;

/**
 * 群聊消息控制层
 *
 * @author makejava
 * @since 2020-06-17 10:51:13
 */
@RestController
@RequestMapping("/groupMsg")
public class GroupMsgContentController {

  @Autowired
  private GroupMsgContentServiceImpl groupMsgContentService;

  @GetMapping("/")
  public KqRespEntity getLimitGroupMsgs() {
    return KqRespEntity.success(groupMsgContentService.getRecentGroupMessages());
  }
}
