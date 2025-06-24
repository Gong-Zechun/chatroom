package top.javahai.chatroom.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.MD5;
import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import top.javahai.chatroom.entity.User;
import top.javahai.chatroom.exception.KqException;
import top.javahai.chatroom.utils.JwtUtil;
import top.javahai.chatroom.utils.RedissonUtils;

@Service
public class LoginService {

  @Autowired
  private RedissonUtils redissonUtils;
  @Autowired
  private UserServiceImpl userService;

  public String login(HttpServletRequest request, String verifycode,  String username, String password) {
    String sessionCaptcha = (String) request.getSession().getAttribute("captcha");
    checkSessionCaptcha(sessionCaptcha);
    checkIfVerifycodeEqualsSessionCaptcha(verifycode, sessionCaptcha);

    checkUsernameIsLegal(username);
    checkPasswordIsLegal(password);

    User temp = userService.getUserByUsername(username);
    if (temp == null) {
      throw new KqException(HttpStatus.BAD_REQUEST.value(), "用户不存在！").notFillInStackTrace();
    }

    User user = userService.getUserByLoginInfo(username, password);
    if (user == null) {
      throw new KqException(HttpStatus.BAD_REQUEST.value(), "密码错误！").notFillInStackTrace();
    }

    request.getSession().setAttribute("username", user.getUsername());
    request.getSession().setAttribute("headpic", user.getHeadpic());
    String clientIP = request.getRemoteHost();
    String token = createToken(username, user.getHeadpic(), request.getRemoteHost());
    saveUserToken(username, token);
    return token;
  }

  /**
   * 注册-ing
   */
  public void signup(
    HttpServletRequest request, String verifycode, String username,
    String password,
    String passwordtoo
  ) {
    String sessionCaptcha = (String) request.getSession().getAttribute("captcha");
    checkSessionCaptcha(sessionCaptcha);
    checkIfVerifycodeEqualsSessionCaptcha(verifycode, sessionCaptcha);
    checkUsernameIsLegal(username);
    checkUsernameIsDuplicate(username);
    checkPasswordIsLegal(password, passwordtoo);

    String headpic = userService.getRandomHeadPic();
    userService.insert(username, DigestUtil.md5Hex(password), headpic);
  }

  private void checkSessionCaptcha(String sessionCaptcha) {
    if (StrUtil.isBlank(sessionCaptcha)) {
      throw new KqException(HttpStatus.BAD_REQUEST.value(), "没有生成验证码，请刷新页面！").notFillInStackTrace();
    }
  }

  private void checkIfVerifycodeEqualsSessionCaptcha(String verifycode, String sessionCaptcha) {
    if (!sessionCaptcha.equalsIgnoreCase(verifycode)) {
      throw new KqException(HttpStatus.BAD_REQUEST.value(), "验证码错误！").notFillInStackTrace();
    }
  }

  private void checkUsernameIsLegal(String username) {
    if (StrUtil.isBlank(username)) {
      throw new KqException(HttpStatus.BAD_REQUEST.value(), "用户名不能为空！").notFillInStackTrace();
    }
    if (username.length() > 20) {
      throw new KqException(HttpStatus.BAD_REQUEST.value(), "用户名不得大于20个字符！").notFillInStackTrace();
    }
  }

  private void checkUsernameIsDuplicate(String username) {
    User user = userService.getUserByUsername(username);
    if (user != null) {
      throw new KqException(HttpStatus.BAD_REQUEST.value(), "用户名重复，请重输！").notFillInStackTrace();
    }
  }

  private void checkPasswordIsLegal(String password, String passwordtoo) {
    checkPasswordIsLegal(password);
    checkPasswordIsLegal(passwordtoo);
    if (!password.equals(passwordtoo)) {
      throw new KqException(HttpStatus.BAD_REQUEST.value(), "两次输入的密码不一致！").notFillInStackTrace();
    }
  }

  private void checkPasswordIsLegal(String password) {
    if (StrUtil.isBlank(password)) {
      throw new KqException(HttpStatus.BAD_REQUEST.value(), "密码不能为空！").notFillInStackTrace();
    }

    if (password.length() > 10) {
      throw new KqException(HttpStatus.BAD_REQUEST.value(), "密码长度不得大于10个字符！").notFillInStackTrace();
    }
  }

  private String createToken(String username, String headpic, String clientIP) {
    Map<String, Object> payload = new HashMap<>();
    payload.put("username", username);
    payload.put("headpic", headpic);
    payload.put("clientip", clientIP);
    return JwtUtil.createToken(payload);
  }

  // 添加用户 Token
  public void saveUserToken(String username, String token) {
    redissonUtils.putToHash("userTokens", username, token);
  }

  // 获取用户 Token
  public String getUserToken(String username) {
    return (String) redissonUtils.getFromHash("userTokens", username);
  }

  // 删除用户 Token
  public void deleteUserToken(String username) {
    redissonUtils.removeFromHash("userTokens", username);
  }

  public List<User> getUsers(String currentUsername) {
    List<User> list = getUsers();
    if (CollectionUtil.isEmpty(list)) {
      throw new KqException(HttpStatus.BAD_REQUEST.value(), "用户列表为空！").notFillInStackTrace();
    }
    moveElementToFront(list, currentUsername);
    return list;
  }

  public List<User> getUsers() {
    Map<String, Object> map = redissonUtils.getHash("userTokens");
    if (MapUtil.isEmpty(map)) {
      return Lists.newArrayList();
    }

    List<User> users = Lists.newArrayList();
    for (String username : map.keySet()) {
      String token = map.get(username).toString();
      Map<String, Object> tokenMap = JwtUtil.parseToken(token);

      User user = new User();
      user.setUsername(tokenMap.get("username").toString());
      user.setHeadpic(tokenMap.get("headpic").toString());
      user.setClientip(tokenMap.get("clientip").toString());
      users.add(user);
    }

    return users;
  }

  public static void moveElementToFront(List<User> list, String currentUsername) {
    // 遍历列表，查找符合条件的元素
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getUsername().equals(currentUsername)) {
        // 将符合条件的元素移动到第一个位置
        User element = list.remove(i); // 移除该元素
        list.add(0, element); // 添加到第一个位置
        break; // 找到后退出循环
      }
    }
  }
}
