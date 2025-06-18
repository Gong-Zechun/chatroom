package top.javahai.chatroom.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import java.util.Date;
import java.util.Map;

public class JwtUtil {

  // 密钥
  private static final byte[] SECRET_KEY = "gzcSecretKey".getBytes();

  /**
   * 生成 JWT Token
   *
   * @param payload 自定义负载数据
   * @return 生成的 JWT Token
   */
  public static String createToken(Map<String, Object> payload) {
    // 设置过期时间为 1 小时
    Date expireDate = new Date(System.currentTimeMillis() + 3600000);
    return JWTUtil.createToken(payload, SECRET_KEY);
  }

  /**
   * 验证 JWT Token
   *
   * @param token JWT Token
   * @return 验证结果
   */
  public static boolean verifyToken(String token) {
    return JWTUtil.verify(token, SECRET_KEY);
  }

  /**
   * 解析 JWT Token
   *
   * @param token JWT Token
   * @return 解析后的负载数据
   */
  public static Map<String, Object> parseToken(String token) {
    if (StrUtil.isBlank(token)) {
      return MapUtil.newHashMap();
    }
    JWT jwt = JWTUtil.parseToken(token);
    return jwt.getPayloads();
  }
}
