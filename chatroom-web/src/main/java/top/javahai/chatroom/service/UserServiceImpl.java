package top.javahai.chatroom.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import top.javahai.chatroom.entity.User;
import top.javahai.chatroom.mapper.UserMapper;

@Service
public class UserServiceImpl {

  @Resource
  private UserMapper userMapper;

  public void insert(String username, String password, String headpic, String clientip) {
    User user = new User();
    user.setUsername(username);
    user.setPassword(password);
    user.setHeadpic(headpic);
    user.setClientip(clientip);
    user.setCreateTime(DateUtil.date());
    user.setModifyTime(DateUtil.date());
    userMapper.insert(user);
  }

  public User getUserByUsername(String username) {
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    wrapper.eq("username", username);
    return userMapper.selectOne(wrapper);
  }

  public User getUserByLoginInfo(String username, String password) {
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    wrapper.eq("username", username);
    wrapper.eq("password", DigestUtil.md5Hex(password));
    return userMapper.selectOne(wrapper);
  }

  public String getRandomHeadPic() {
    int index = RandomUtil.randomInt(1, 11);
    return "assets/images/xs/avatar" + index + ".jpg";
  }
}
