package top.javahai.chatroom.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import java.time.LocalDateTime;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * 自动填充字段处理器
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

  @Override
  public void insertFill(MetaObject metaObject) {
    this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
    this.strictInsertFill(metaObject, "modifyTime", LocalDateTime::now, LocalDateTime.class);
    // 可以设置当前登录用户
    this.strictInsertFill(metaObject, "createBy", () -> "system", String.class);
    this.strictInsertFill(metaObject, "modifyBy", () -> "system", String.class);
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    this.strictUpdateFill(metaObject, "modifyTime", LocalDateTime::now, LocalDateTime.class);
    // 可以设置当前登录用户
    this.strictUpdateFill(metaObject, "modifyBy", () -> "system", String.class);
  }
}
