package top.javahai.chatroom.service;

import static top.javahai.chatroom.constant.GlobalConstants.*;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import top.javahai.chatroom.entity.User;
import top.javahai.chatroom.entity.UserVO;
import top.javahai.chatroom.exception.KqException;
import top.javahai.chatroom.utils.JwtUtil;
import top.javahai.chatroom.utils.RedissonUtils;

@Service
public class LoginService {

  @Autowired
  private RedissonUtils redissonUtils;
  @Autowired
  private UserServiceImpl userService;

  // Token过期时间(单位：分钟)
  private static final long TOKEN_EXPIRE = 10;
  private static final TimeUnit TOKEN_EXPIRE_UNIT = TimeUnit.MINUTES;

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
    String token = createToken(username, user.getHeadpic(), request.getRemoteHost());

    saveUserInfoToRedis(refreshLastVisitTime(user), token);
    return token;
  }

  public UserVO refreshLastVisitTime(User user) {
    UserVO userVO = new UserVO();
    BeanUtils.copyProperties(user, userVO);
    userVO.setLastVisitTime(DateUtil.date());
    return userVO;
  }

  /**
   * 登录成功后保存用户信息到Redis
   */
  public void saveUserInfoToRedis(UserVO userVO, String token) {
    // 1. 存储token -> 用户信息
    String tokenKey = String.format(USER_TOKEN_KEY, token);
    redissonUtils.set(tokenKey, userVO);
    redissonUtils.expire(tokenKey, TOKEN_EXPIRE, TOKEN_EXPIRE_UNIT);

    // 2. 存储userId -> token映射(用于单点登录)
//    String loginKey = String.format(USER_LOGIN_KEY, user.getId());
//    redissonUtils.set(loginKey, token);
//    redissonUtils.expire(loginKey, TOKEN_EXPIRE, TOKEN_EXPIRE_UNIT);

    // 3. 存储用户基本信息到Hash
    saveUserInfo(userVO);

    // 4. 将用户ID添加到全局集合
    redissonUtils.addToSet(USER_IDS_KEY, userVO.getId().toString());
  }

  /**
   * 保存/更新用户基本信息
   */
  public void saveUserInfo(UserVO userVO) {
    String infoKey = String.format(USER_INFO_KEY, userVO.getId().toString());

    redissonUtils.putToHash(infoKey, "id", userVO.getId());
    redissonUtils.putToHash(infoKey, "username", userVO.getUsername());
    redissonUtils.putToHash(infoKey, "headpic", userVO.getHeadpic());
    redissonUtils.putToHash(infoKey, "lastVisitTime", DateUtil.date());

    redissonUtils.expire(infoKey, TOKEN_EXPIRE, TOKEN_EXPIRE_UNIT);
  }

  public User getLoginUser(String token) {
    String key = String.format(USER_TOKEN_KEY, token);
    return (User) redissonUtils.get(key);
  }

  /**
   * 获取所有用户信息
   *
   * @return 用户列表
   */
  public List<UserVO> getAllUsers(String currentUsername) {
    // 1. 获取所有用户ID
    Set<Object> userIds = redissonUtils.getSet(USER_IDS_KEY);
    if (userIds == null || userIds.isEmpty()) {
      return Collections.emptyList();
    }

    // 2. 批量获取用户信息
    List<UserVO> list =  userIds.stream()
      .map(userId -> {
        String infoKey = String.format(USER_INFO_KEY, ((Object[]) userId)[0]);
        Map<String, Object> userMap = redissonUtils.getHash(infoKey);
        return convertMapToUser(userMap);
      })
      .filter(Objects::nonNull)
      .collect(Collectors.toList());

    moveElementToFront(list, currentUsername);
    return list;
  }

  private UserVO convertMapToUser(Map<String, Object> map) {
    if (map == null || map.isEmpty()) {
      return null;
    }

    UserVO userVO  = new UserVO();
    userVO.setId(Integer.parseInt(map.get("id").toString()));
    userVO.setUsername((String) map.get("username"));
    userVO.setHeadpic((String) map.get("headpic"));
    userVO.setLastVisitTime((Date) map.get("lastVisitTime"));
    userVO.setLastVisitTimeStr(map.get("lastVisitTime").toString());
    return userVO;
  }

  /**
   * 删除用户登录信息
   * @param token 用户token
   */
  public void removeLoginUser(String token) {
    User user = getLoginUser(token);
    if (user != null) {
      // 1. 删除token映射
      String tokenKey = String.format(USER_TOKEN_KEY, token);
      redissonUtils.delete(tokenKey);

      // 2. 删除userId->token映射
      String loginKey = String.format(USER_LOGIN_KEY, user.getId());
      redissonUtils.delete(loginKey);
    }
  }

  /**
   * 刷新token有效期
   * @param token 用户token
   */
  public void refreshToken(String token) {
    String tokenKey = String.format(USER_TOKEN_KEY, token);
    redissonUtils.expire(tokenKey, TOKEN_EXPIRE, TOKEN_EXPIRE_UNIT);

    User user = getLoginUser(token);
    if (user != null) {
      String loginKey = String.format(USER_LOGIN_KEY, user.getId());
      redissonUtils.expire(loginKey, TOKEN_EXPIRE, TOKEN_EXPIRE_UNIT);

      String infoKey = String.format(USER_INFO_KEY, user.getId().toString());
      redissonUtils.expire(infoKey, TOKEN_EXPIRE, TOKEN_EXPIRE_UNIT);
    }
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

  public static void moveElementToFront(List<UserVO> list, String currentUsername) {
    // 遍历列表，查找符合条件的元素
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getUsername().equals(currentUsername)) {
        // 将符合条件的元素移动到第一个位置
        UserVO element = list.remove(i); // 移除该元素
        list.add(0, element); // 添加到第一个位置
        break; // 找到后退出循环
      }
    }
  }

  public void checkToken(String token) {
    if (StrUtil.isBlank(token)) {
      throw new KqException(HttpStatus.BAD_REQUEST.value(), "登录token为空，，请重新登录！").notFillInStackTrace();
    }

    User user = getLoginUser(token);
    if (user == null) {
      throw new KqException(HttpStatus.BAD_REQUEST.value(), "token已过期，请重新登录！").notFillInStackTrace();
    }
  }
}
