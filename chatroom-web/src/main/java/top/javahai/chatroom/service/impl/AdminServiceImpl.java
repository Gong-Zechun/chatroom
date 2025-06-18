package top.javahai.chatroom.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import top.javahai.chatroom.entity.Admin;
import top.javahai.chatroom.dao.AdminDao;
import top.javahai.chatroom.service.AdminService;

/**
 * (Admin)表服务实现类
 *
 * @author makejava
 * @since 2020-06-16 11:35:58
 */
@Service("adminService")
public class AdminServiceImpl implements AdminService {

  @Resource
  private AdminDao adminDao;

  /**
   * 根据用户名进行登录
   *
   * @param username
   * @return
   * @throws
   */
  public Admin loadUserByUsername(String username) throws Exception {
    Admin admin = adminDao.loadUserByUsername(username);
    if (admin == null) {
      throw new Exception("找不到该管理员");
    }
    return admin;
  }

  /**
   * 通过ID查询单条数据
   *
   * @param id 主键
   * @return 实例对象
   */
  @Override
  public Admin queryById(Integer id) {
    return this.adminDao.queryById(id);
  }

  /**
   * 查询多条数据
   *
   * @param offset 查询起始位置
   * @param limit  查询条数
   * @return 对象列表
   */
  @Override
  public List<Admin> queryAllByLimit(int offset, int limit) {
    return this.adminDao.queryAllByLimit(offset, limit);
  }

  /**
   * 新增数据
   *
   * @param admin 实例对象
   * @return 实例对象
   */
  @Override
  public Admin insert(Admin admin) {
    this.adminDao.insert(admin);
    return admin;
  }

  /**
   * 修改数据
   *
   * @param admin 实例对象
   * @return 实例对象
   */
  @Override
  public Admin update(Admin admin) {
    this.adminDao.update(admin);
    return this.queryById(admin.getId());
  }

  /**
   * 通过主键删除数据
   *
   * @param id 主键
   * @return 是否成功
   */
  @Override
  public boolean deleteById(Integer id) {
    return this.adminDao.deleteById(id) > 0;
  }

}
