package top.javahai.chatroom.entity;

import cn.hutool.core.date.DateTime;
import lombok.Data;

/**
 * 用户实体类
 */
@Data
public class User {
  /**
   * 用户ID
   */
  private Integer id;

  /**
   * 登录账号
   */
  private String username;

  /**
   * 密码
   */
  private String password;

  /**
   * 用户头像
   */
  private String headpic;

  /**
   * 客户端IP
   */
  private String clientip;

  /**
   * 是否被锁定 (0-未锁定, 1-已锁定)
   */
  private Integer isLocked;

  /**
   * 是否被删除 (0-未删除, 1-已删除)
   */
  private Integer isDeleted;

  /**
   * 创建时间
   */
  private DateTime createTime;

  /**
   * 创建者
   */
  private String createBy;

  /**
   * 修改时间 (根据当前时间戳更新)
   */
  private DateTime modifyTime;

  /**
   * 更新者
   */
  private String modifyBy;
}
