/*
 * Copyright (C) 2019 KQ GEO Technologies Co., Ltd.
 * All rights reserved.
 */
package top.javahai.chatroom.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * web请求状态码枚举类
 *
 * @author TuHao
 */
@Getter
@AllArgsConstructor
public enum KqRespCode {

  /* 兼容Http status返回码 */
  /** 请求成功 */
  SUCCESS(HttpStatus.OK.value(), "http.status.ok"),
  /** 请求失败 */
  BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "http.status.badRequest"),
  /** 未授权 */
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "http.status.unauthorized"),
  /** 禁止访问 */
  FORBIDDEN(HttpStatus.FORBIDDEN.value(), "http.status.forbidden"),
  /** 服务不存在 */
  NOT_FOUND(HttpStatus.NOT_FOUND.value(), "http.status.notFound"),
  /** 不支持该方法 */
  METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED.value(), "http.status.methodNotAllowed"),
  /** 响应超时 */
  REQUEST_TIMEOUT(HttpStatus.REQUEST_TIMEOUT.value(), "http.status.requestTimeout"),
  /** 响应超时 */
  UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), "http.status.unsupportedMediaType"),
  /** 异常 */
  EXPECTATION_FAILED(HttpStatus.EXPECTATION_FAILED.value(), "http.status.exceptionFailed"),
  /** 请求次数超过限制 */
  TOO_MANY_REQUEST(HttpStatus.TOO_MANY_REQUESTS.value(), "http.status.tooManyRequests"),
  /** 内部错误 */
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "http.status.internalServerError"),
  /** 服务不可用 */
  SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE.value(), "http.status.serviceUnavailable"), GATEWAY_TIMEOUT(
      HttpStatus.GATEWAY_TIMEOUT.value(), "http.status.gatewayTimeout"), NETWORK_AUTHENTICATION_REQUIRED(
          HttpStatus.NETWORK_AUTHENTICATION_REQUIRED.value(),
          "http.status.networkAuthRequired"),

  /* 自定义异常返回码 */
  /** Token */
  TOKEN_APPLY_INFO_INVALID(1001, "token.apply.info.invalid"), TOKEN_INVALID(1003,
      "token.invalid"), TOKEN_ACCOUNT_ALIAS_EXCEPTION(1004, "token.account.alias.exception"), TOKEN_ACCOUNT_NOT_EXISTS(
          1006, "token.account.notExists"),

  /** http */
  HTTP_METHOD_NOT_SUPPORTED(1050, "http.method.notSupported"),

  /** 集群/服务异常 */
  CLUSTER_NOT_AVAILABLE(2001, "cluster.notAvailable"), NODE_REGADDR_GET_FAILED(2002,
      "node.regAddr.get.failed"), NODE_REGADDR_DUP(2003, "node.regAddr.dup"), NODE_NOT_STARTED(2004,
          "node.notStarted"), NODE_NOT_AVAILABLE(2005, "node.notAvailable"), NODE_HEALTHY_CHECK_TIMEOUT(2006,
              "node.healthy.check.timeout"), NODE_HEALTHY_CHECK_FAILED(2007,
                  "node.healthy.check.failed"), NODE_SERVICE_ALIAS_EMPTY(2008,
                      "node.service.alias.empty"), NODE_REGISTERED(2009, "node.registered"), NODE_NOT_EXISTS(2010,
                          "node.notExists"),

  SERVICE_THUMB_UPDATE_FAILD(2050, "service.thumb.update.failed"), SERVICE_PROPS_GET_FAILED(2051,
      "service.props.get.failed"), SERVICE_NODES_STOPPED_NOT_EXISTS(2052,
          "service.nodes.stopped.notExists"), SERVICE_NODES_EMPTY(2053, "service.nodes.empty"), SERVICE_NAME_DUP(2054,
              "service.name.dup"), SERVICE_NOT_EXISTS(2055, "service.notExists"), SERVICE_DELETE_FAILED(2056,
                  "service.delete.failed"), SERVICE_DIR_NOT_EXIST(2057,
                      "service.dir.notExists"), SERVICE_KQMD_GET_FAILED(2058,
                          "service.kqmd.get.failed"), SERVICE_OPERATION_FAILED(2059,
                              "service.operation.failed"), SERVICE_PERM_UPDATE_FAILED(2060,
                                  "service.perm.update.failed"), SERVICE_GET_SERVICE_OPERATOR_LIST_FAILED(2061,
                                      "service.get.service.operator.list.failed"), SERVICE_STOP_FAILED(2062,
                                          "service.stop.failed"), SERVICE_UPDATE_FAILED(2063,
                                              "service.update.failed"), SERVICE_START_FAILED(2064,
                                                  "service.start.failed"), SERVICE_ALIAS_EXIST(2065,
                                                      "service.alias.exist"), SERVICE_FOLDER_NAME_DUP(2066,
                                                          "service.folder.name.dup"),

  FOLDER_NAME_DUP(2100, "folder.name.dup"), FOLDER_NO_DATA(2101, "folder.no.data"),

  SERVICE_LIST_NOT_EXISTS(2101, "service.list.notExists"),

  /** ServerManager */
  ACCOUNT_DUP(2300, "servermgr.account.dup"), ACCOUNT_OR_PASSWORD_ERROR(2301,
      "servermgr.account.password.error"), ACCOUNT_OLD_PASSWORD_ERROR(2302,
          "servermgr.account.password.old.error"), NO_AUTH(2303, "servermgr.no.auth"), UA_TOKEN_EMPTY(401,
              "servermgr.uatoken.empty"), ACCOUNT_NOT_FOUND(2405,
                  "servermgr.account.notfound"), ACCOUNT_INFO_QUERY_FAILED(2046,
                      "servermgr.account.query.failed"), NO_ANONYMOUS_PERMISSION(2047,
                          "servermgr.no.anonymous.permission"),

  /** 告警异常 */
  ALERT_EXCEPTION_CONFIG_FILE_NOT_EXIST(3001, "告警配置文件不存在"), ALERT_EXCEPTION_WEBHOOK_CONFIG_ERROR(3002, "webhook配置错误"),

  /** consul异常 */
  INSTANTCE_NOT_EXIST(4001, "实例不存在"), CONSUL_DELETE_KV_ERROR(4002, "删除k/v异常"),

  /** 数据校验异常 例如表单数据验证不通过等 */
  PARAM_VALUE_INVALID(5002, "param.value.invalid"),
  /** 参数丢失 */
  PARAM_MISSING(5003, "param.missing"),

  /**
   * <p>
   * 业务错误. BizException is known Exception, no need retry. Refer To COLA
   * <p>
   * {@link https://github.com/alibaba/COLA/blob/master/cola-components/cola-component-exception/src/main/java/com/alibaba/cola/exception/BizException.java}
   */
  EXCEPTION_BIZ(5555, "bizException"),
  //
  /**
   * 致命错误. System Exception is unexpected Exception, retry might work again. Refer To COLA
   */
  EXCEPTION_SYS(6666, "sysException"),

  MONITOR_VALIDATE_EMAIL_USABILITY_FAIL(800000001, "monitor.alert.email.verify.fail"),

  /** 内部使用接口 */
  // 静态文件映射重复
  // 错误码分三段:
  // 1. 500 服务端错误
  // 2. 999 内部模块
  // 3. 001 错误码
  INNER_STATIC_RESOURCE_MAPPING_DUPLICATE(500999001, "inner.srm.duplicate");

  private final int code;
  private final String msg;

  public String getMsg() {
    return TranslateUtil.getMsg(msg);
  }

  public String getMsg(Object[] params) {
    return TranslateUtil.getMsg(msg, params);
  }

  public static String getMsgByCode(int code) {
    for (KqRespCode meta : KqRespCode.values()) {
      if (meta.getCode() == code) {
        return TranslateUtil.getMsg(meta.msg);
      }
    }
    return null;
  }
}
