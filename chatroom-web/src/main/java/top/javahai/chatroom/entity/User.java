package top.javahai.chatroom.entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.*;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import org.apache.ibatis.type.JdbcType;

/**
 * 用户实体类
 */
@Data
@Accessors(chain = true)
@TableName("user")
public class User {
  /**
   * 用户ID
   */
  @TableId(type = IdType.AUTO)
  private Integer id;

  /**
   * 登录账号
   */
  private String username;

  /**
   * 密码(存储加密后的密码)
   */
  private String password;

  /**
   * 用户头像URL
   */
  private String headpic;

  /**
   * 客户端IP
   */
  private String clientip;

  /**
   * 是否被锁定(0-未锁定,1-已锁定)
   */
  private Integer isLocked;

  /**
   * 逻辑删除标志(0-未删除,1-已删除)
   */
  @TableLogic
  private Integer isDeleted;

  /**
   * 创建时间
   */
  @TableField(fill = FieldFill.INSERT)
//  @TableField(value = "create_time", jdbcType = JdbcType.TIMESTAMP)
  private Date createTime;

  /**
   * 创建人
   */
  @TableField(fill = FieldFill.INSERT)
  private String createBy;

  /**
   * 更新时间
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Date modifyTime;

  /**
   * 更新人
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private String modifyBy;
}
