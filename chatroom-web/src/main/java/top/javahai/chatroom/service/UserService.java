package top.javahai.chatroom.service;



import java.util.List;
import top.javahai.chatroom.entity.RespPageBean;
import top.javahai.chatroom.entity.User_Old;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2020-06-16 11:37:09
 */
public interface UserService {


    /**
     * 获取除了当前登录用户的所有user表的数据
     * @return
     */
    List<User_Old> getUsersWithoutCurrentUser();

    /**
     * 设置用户当前状态为在线
     * @param id 用户id
     */
    public void setUserStateToOn(Integer id);

    /**
     * 设置用户当前状态为离线
     * @param id
     */
    public void setUserStateToLeave(Integer id);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User_Old queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<User_Old> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    Integer insert(User_Old user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    Integer update(User_Old user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 检查用户名是否已存在
     * @param username
     * @return
     */
    Integer checkUsername(String username);

    /**
     * 检查昵称是否存在
     * @param nickname
     * @return
     */
    Integer checkNickname(String nickname);

    RespPageBean getAllUserByPage(Integer page, Integer size,  String keyword,Integer isLocked);

    Integer changeLockedStatus(Integer id, Boolean isLocked);

    Integer deleteByIds(Integer[] ids);
}
