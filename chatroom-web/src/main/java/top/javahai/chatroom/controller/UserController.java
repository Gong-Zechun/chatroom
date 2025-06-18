package top.javahai.chatroom.controller;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.javahai.chatroom.entity.RespPageBean;
import top.javahai.chatroom.entity.User_Old;
import top.javahai.chatroom.service.UserService;
import top.javahai.chatroom.utils.KqRespEntity;

/**
 * 用户控制器
 *
 * @author makejava
 * @since 2020-06-16 11:37:09
 */
@RestController
@RequestMapping("/user")
public class UserController {

  /**
   * 服务对象
   */
  @Resource
  private UserService userService;

  /**
   * 注册操作
   */
  @PostMapping("/register")
  public KqRespEntity addUser(@RequestBody User_Old user) {
    if (userService.insert(user) == 1) {
      return KqRespEntity.success("注册成功！");
    } else {
      return KqRespEntity.badRequest("注册失败！");
    }
  }

  /**
   * 注册操作，检查用户名是否已被注册
   *
   * @param username
   * @return
   */
  @GetMapping("/checkUsername")
  public Integer checkUsername(@RequestParam("username") String username) {
    return userService.checkUsername(username);
  }

  /**
   * 注册操作，检查昵称是否已被注册
   *
   * @param nickname
   * @return
   */
  @GetMapping("/checkNickname")
  public Integer checkNickname(@RequestParam("nickname") String nickname) {
    return userService.checkNickname(nickname);
  }

  /**
   * 通过主键查询单条数据
   *
   * @param id 主键
   * @return 单条数据
   */
  @GetMapping("selectOne")
  public User_Old selectOne(Integer id) {
    return this.userService.queryById(id);
  }

  /**
   * @param page 页数，对应数据库查询的起始行数
   * @param size 数据量，对应数据库查询的偏移量
   * @param keyword 关键词，用于搜索
   * @param isLocked 是否锁定，用于搜索
   * @return
   */
  @GetMapping("/")
  public RespPageBean getAllUserByPage(
      @RequestParam(value = "page", defaultValue = "1") Integer page,
      @RequestParam(value = "size", defaultValue = "10") Integer size,
      String keyword, Integer isLocked
  ) {
    return userService.getAllUserByPage(page, size, keyword, isLocked);
  }

  /**
   * 更新用户的锁定状态
   *
   * @param id
   * @param isLocked
   * @return
   */
  @PutMapping("/")
  public KqRespEntity changeLockedStatus(
      @RequestParam("id") Integer id,
      @RequestParam("isLocked") Boolean isLocked
  ) {
    if (userService.changeLockedStatus(id, isLocked) == 1) {
      return KqRespEntity.success("更新成功！");
    } else {
      return KqRespEntity.badRequest("更新失败！");
    }
  }

  /**
   * 删除单一用户
   *
   * @param id
   * @return
   */
  @DeleteMapping("/{id}")
  public KqRespEntity deleteUser(@PathVariable Integer id) {
    if (userService.deleteById(id)) {
      return KqRespEntity.success("删除成功！");
    } else {
      return KqRespEntity.badRequest("删除失败！");
    }
  }

  /**
   * 批量删除用户
   *
   * @param ids
   * @return
   * @author luo
   */
  @DeleteMapping("/")
  public KqRespEntity deleteUserByIds(Integer[] ids) {
    if (userService.deleteByIds(ids) == ids.length) {
      return KqRespEntity.success("删除成功！");
    } else {
      return KqRespEntity.badRequest("删除失败！");
    }
  }

  @GetMapping("/users")
  public List<User_Old> getUsersWithoutCurrentUser() {
    return userService.getUsersWithoutCurrentUser();
  }
}
