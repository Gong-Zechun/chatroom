/*
 * Copyright (C) 2019 KQ GEO Technologies Co., Ltd.
 * All rights reserved.
 */
package top.javahai.chatroom.utils;

import cn.hutool.core.util.ObjectUtil;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

/**
 * web接口统一返回类型
 *
 * @author TuHao
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KqRespEntity<T> {

  private String msg;
  private Integer code;

  /* 兼容portal返回类型 */
  public Integer getStatus() {
    return code;
  }

  private T data;

  public static final KqRespEntity<String> SUCCESS = KqRespEntity.<String>builder().code(
          KqRespCode.SUCCESS.getCode())
      .msg(KqRespCode.SUCCESS.getMsg()).build();

  public static <T> KqRespEntity<T> success(T data) {
    return KqRespEntity.<T>builder().data(data).code(KqRespCode.SUCCESS.getCode()).msg(
            KqRespCode.SUCCESS.getMsg())
        .build();
  }

  public static <T> KqRespEntity<T> makeResp(KqRespCode code, T data) {
    return KqRespEntity.<T>builder().data(data).code(code.getCode()).msg(code.getMsg()).build();
  }

  public static KqRespEntity<String> makeResp(KqRespCode code) {
    return KqRespEntity.<String>builder().code(code.getCode()).msg(code.getMsg()).build();
  }

  public static KqRespEntity<String> makeResp(KqRespCode code, String msg) {
    return makeResp(code.getCode(), msg);
  }

  public static KqRespEntity<String> badRequest(String msg) {
    return makeResp(KqRespCode.BAD_REQUEST.getCode(), msg);
  }

  public static KqRespEntity<String> makeResp(int code, String msg) {
    return KqRespEntity.<String>builder().code(code).msg(msg).build();
  }

  public static <T> KqRespEntity<T> makeResp(KqRespCode code, String msg, T data) {
    return makeResp(code.getCode(), msg, data);
  }

  public static <T> KqRespEntity<T> makeResp(int code, String msg, T data) {
    return KqRespEntity.<T>builder().code(code).msg(msg).data(data).build();
  }

  /**
   * 判断返回是否为成功
   *
   * @param result
   * Result
   * 
   * @return 是否成功
   */
  public static boolean isSuccess(@Nullable KqRespEntity<?> result) {
    return Optional.ofNullable(result)
        .map(x -> ObjectUtil.equal(KqRespEntity.SUCCESS.code, x.code))
        .orElse(Boolean.FALSE);
  }

  /**
   * 判断返回是否为成功
   *
   * @param result
   * Result
   * 
   * @return 是否成功
   */
  public static boolean isNotSuccess(@Nullable KqRespEntity<?> result) {
    return !KqRespEntity.isSuccess(result);
  }
}
