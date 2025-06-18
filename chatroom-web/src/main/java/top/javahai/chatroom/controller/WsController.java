//package top.javahai.chatroom.controller;
//
//import com.github.binarywang.java.emoji.EmojiConverter;
//import java.util.Date;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//import top.javahai.chatroom.entity.GroupMsgContent;
//import top.javahai.chatroom.entity.Message;
//import top.javahai.chatroom.entity.User_Old;
//import top.javahai.chatroom.service.GroupMsgContentService;
//
///**
// * @author Hai
// * @date 2020/6/16 - 23:34
// */
//@Controller
//public class WsController {
//
//  @Autowired
//  SimpMessagingTemplate simpMessagingTemplate;
//
//  @Autowired
//  GroupMsgContentService groupMsgContentService;
//
//  EmojiConverter emojiConverter = EmojiConverter.getInstance();
//
////  /**
////   * 单聊的消息的接受与转发
////   *
////   * @param message
////   */
////  @MessageMapping("/ws/chat")
////  public void handleMessage(Message message) {
////    User_Old user = new User_Old();
////    message.setFromNickname(user.getNickname());
////    message.setFrom(user.getUsername());
////    message.setCreateTime(new Date());
////    simpMessagingTemplate.convertAndSendToUser(message.getTo(), "/queue/chat", message);
////  }
//
//  /**
//   * 群聊的消息接受与转发
//   *
//   * @param groupMsgContent
//   */
//  @MessageMapping("/ws/groupChat")
//  public void handleGroupMessage(GroupMsgContent groupMsgContent) {
//    User_Old currentUser = new User_Old();
//    //处理emoji内容,转换成unicode编码
//    groupMsgContent.setContent(emojiConverter.toHtml(groupMsgContent.getContent()));
//    //保证来源正确性，从Security中获取用户信息
//    groupMsgContent.setFromId(currentUser.getId());
//    groupMsgContent.setFromName(currentUser.getNickname());
//    groupMsgContent.setFromProfile(currentUser.getUserProfile());
//    groupMsgContent.setCreateTime(new Date());
//    //保存该条群聊消息记录到数据库中
//    groupMsgContentService.insert(groupMsgContent);
//    //转发该条数据
//    simpMessagingTemplate.convertAndSend("/topic/greetings", groupMsgContent);
//  }
//}
