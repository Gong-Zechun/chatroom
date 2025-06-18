package top.javahai.chatroom.controller;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.javahai.chatroom.entity.Admin;
import top.javahai.chatroom.service.AdminService;

/**
 * (Admin)表控制层
 *
 * @author makejava
 * @since 2020-06-16 11:35:59
 */
@RestController
@RequestMapping("admin")
public class AdminController {
  @Resource
  private AdminService adminService;

  /**
   * 通过主键查询单条数据
   *
   * @param id 主键
   * @return 单条数据
   */
  @GetMapping("selectOne")
  public Admin selectOne(Integer id) {
    return adminService.queryById(id);
  }
}
