package top.javahai.chatroom.exception;

import java.sql.SQLException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.javahai.chatroom.utils.KqRespEntity;

/**
 * @author Hai
 * @date 2020/4/27 - 19:49 功能：全局异常处理类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /*
  处理SQLException异常
   */
  @ExceptionHandler(SQLException.class)
  public KqRespEntity sqlExceptionHandler(SQLException e) {
    e.printStackTrace();
    return KqRespEntity.badRequest("数据库异常，操作失败！");
  }
}
