package top.javahai.chatroom.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.map.MapUtil;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.javahai.chatroom.service.LoginService;
import top.javahai.chatroom.utils.KqRespEntity;

/**
 * 登录控制器
 *
 * @Author Gong Zechun
 * @Date 2025/6/11 - 16:56
 */
@RestController
public class LoginController {

  @Autowired
  private LoginService loginService;

  /**
   * 获取验证码图片写到响应的输出流中，保存验证码到session
   */
  @GetMapping("/captcha")
  public void getCaptcha(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(130, 44, 4, 0);
    request.getSession().setAttribute("captcha", captcha.getCode());
    captcha.write(response.getOutputStream());
  }

  @PostMapping("/login")
  public KqRespEntity login(
    HttpServletRequest request, @RequestParam String verifycode, @RequestParam String username,
    @RequestParam String password
  ) {
    String token = loginService.login(request, verifycode, username, password);
    return KqRespEntity.success(MapUtil.of("token", token));
  }

  @PostMapping("/signup")
  public KqRespEntity signup(
    HttpServletRequest request, @RequestParam String verifycode, @RequestParam String username,
    @RequestParam String password,
    @RequestParam String passwordtoo
  ) {
    loginService.signup(request, verifycode, username, password, passwordtoo);
    return KqRespEntity.SUCCESS;
  }

  /**
   * 获取用户列表
   */
  @GetMapping("/users")
  public KqRespEntity getUsers(String currentUsername) {
    return KqRespEntity.success(loginService.getAllUsers(currentUsername));
  }
}
