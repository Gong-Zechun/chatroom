/*
 * Copyright (C) 2019 KQ GEO Technologies Co., Ltd.
 * All rights reserved.
 */
package top.javahai.chatroom.constant;

import cn.hutool.core.util.StrUtil;

/**
 * 全局变量
 *
 * @author TuHao
 */
public class GlobalConstants {

  /**
   * 微服务名称
   */
  public static final String GATEWAY = "api-gateway";
  public static final String GATEWAY_ALIAS = "gateway";
  public static final String GIS_SERVICE = "gis-service";
  public static final String GIS_SERVICE_DATA_STORE = "data-store";
  public static final String SERVICE_UPMS = "userAuth";
  // 配置在 Consul KV中的 api-gateway-zuul 配置项中
  public static final String GIS_MANAGER_ALIAS = "gismgr";
  public static final String SERVER_MANAGER = "server-manager";
  /**
   * @deprecated 该GO组件已经移除
   */
  @Deprecated
  public static final String WEBMAP_ALIAS = "view";
  /**
   * gisService日志类型名称
   */
  public static final String SERVICE_SOURCE_TYPE_GISSERVICE = "gisService";
  /**
   * 第三方日志类型名称
   */
  public static final String SERVICE_SOURCE_TYPE_THIRDPART = "thirdPart";
  /**
   * 健康检查uri
   */
  public static final String HEALTH_CHECK_END_PATTERN = "/mnt/health";

  public static final String MANAGER_HEALTH_CHECK_END_PATTERN = "/manager/health";

  /**
   * metrics uri
   */
  public static final String METRICS_END_PATTERN = "/mnt/prometheus";

  /**
   * 代理服务后缀
   */
  public static final String PROXY_END_PATTERN = "-proxy";

  /**
   * 用于在 url 中替换 "服务名-proxy"
   */
  public static final String PROXY_REPLACED_PATTERN = "gis";
  /**
   * 用户id后缀
   */
  public static final String USER_CID_PATTERN = "-cid";
  /**
   * GisServer服务ContextPath
   */
  public static final String GIS_SERVER_CONTEXT_PATH = StrUtil.isBlank(System.getenv("KQGIS_CTX_PATH")) ? "/kqgis"
      : System.getenv("KQGIS_CTX_PATH");

  /**
   * {@link /gis/kqgis/}
   */
  public static final String URI_GIS_SERVICE_PREFIX = StrUtil.format("/{}{}/", PROXY_REPLACED_PATTERN,
      GIS_SERVER_CONTEXT_PATH);

  /**
   * swagger api路径
   */
  public static final String SWAGGER_API_PATH = "/v3/api-docs";

  /**
   * 开发版本标识
   */
  public static final String PROFILE_DEV = "dev";

  /**
   * token参数名
   */
  public static final String PARAM_TOKEN = StrUtil.isBlank(System.getenv("TOKEN_NAME")) ? "ua_token"
      : System.getenv("TOKEN_NAME");

  /**
   * 语言环境参数名称
   */
  public static final String LANG_PARAM = "lang";

  /**
   * 中文
   */
  public static final String ZH_CN_LANG = "zh-CN";

  /**
   * 英文
   */
  public static final String EN_US_LANG = "en-US";

  /**
   * 客户端ip
   */
  public static final String CLIENT_IP = "cip";
  /**
   * 租户代码
   */
  public static final String TENANT_CODE = "key_current_tenant_code";

  /**
   * redis key分隔符
   */
  public static final String REDIS_KEY_SEPERATOR = "::";

  /**
   * 逗号分隔符
   */
  public static final String COMMA_SEPERATOR = ",";

  /**
   * 点分隔符
   */
  public static final String DOT_SEPERATOR = ".";

  /**
   * -分隔符
   */
  public static final String PROXY_NAME_SEPERATOR = "-";

  /**
   * http协议
   */
  public static final String HTTP_PROTOCOL = "http://";
  /**
   * https协议
   */
  public static final String HTTPS_PROTOCOL = "https://";

  /**
   * gisserver返回的json数据中使用的key
   */
  public static final String GIS_SERVER_CODE_KEY = "resultcode";
  public static final String GIS_SERVER_MSG_KEY = "message";
  public static final String GIS_SERVER_SERVICES_KEY = "services";
  public static final String GIS_SERVER_RESULT_KEY = "result";
  public static final String GIS_SERVER_FOLDERS_KEY = "folders";

  public static final String GIS_SERVER_CODE_OK = "success";
  public static final String GIS_SERVER_CODE_NG = "error";

  /**
   * 文件类型瓦片
   */
  public static final String GIS_SERVER_CACHE_FILEPATH = "filepath";

  /**
   * 服务根目录标识
   */
  public static final String SERVICE_ROOT_DIR = "{root}";
  public static final String SEPERATOR = "--%--";
  /**
   * 公共服务根目录标识
   */
  public static final String SERVICE_PUBLIC_DIR = "{public}";
  /**
   * 大数据服务根目录标识
   */
  public static final String SERVICE_BIG_DATA_DIR = "{bigData}";

  public static final String CONSUL_HEALTH_STATUS = "passing";
  public static final String CONSUL_CRITICAL_STATUS = "critical";

  /**
   * consul中存储的服务类型
   */
  public static final String CONSUL_SERVICE_TYPE = "service_type";
  /**
   * consul中存储的服务别名
   */
  public static final String CONSUL_SERVICE_ALIAS = "service_alias";

  /**
   * consul中非HTTP协议服务的tag
   */
  public static final String CONSUL_NONSECURE_TAG = "secure=false";

  /**
   * 服务状态
   */
  public static final String SERVICE_NOT_EXISTS = "NotExists";

  public static final String SERVICE_STARTING = "starting";
  public static final String SERVICE_RUNNING = "running";
  public static final String SERVICE_STOPPING = "stopping";
  public static final String SERVICE_STOPPED = "stopped";
  public static final String SERVICE_NO_STATE = "noState";

  /**
   * 服务操作：启动、停止、删除和编辑
   */
  public static final String SERVICE_ACTION_START = "start";
  public static final String SERVICE_ACTION_STOP = "stop";
  public static final String SERVICE_ACTION_DELETE = "delete";
  public static final String SERVICE_ACTION_EDIT = "edit";

  /**
   * 服务类型
   */
  // 地图服务
  public static final String SERVICE_TYPE_MAP_SERVICE = "MapService";
  // 数据服务
  public static final String SERVICE_TYPE_DATA_SERVICE = "DataService";
  // 三维服务
  public static final String SERVICE_TYPE_3D_SERVICE = "3DService";
  // 网络分析服务
  public static final String SERVICE_TYPE_NETWORK_SERVICE = "NetworkService";
  // 动态标绘服务
  public static final String SERVICE_TYPE_PLOTTING_SERVICE = "PlottingService";
  // 空间分析服务
  public static final String SERVICE_TYPE_SPATIAL_ANALYSIS_SERVICE = "SpatialAnalysisService";
  // 影像服务
  public static final String SERVICE_TYPE_IMAGE_SERVICE = "ImageService";
  // 聚合服务
  public static final String SERVICE_TYPE_MAP_SERVICE_AGGREGATION = "AggrService";
  // we打印服务
  public static final String SERVICE_TYPE_WEB_PRINT_SERVICE = "WebPrintService";
  // 公共服务
  public static final String SERVICE_TYPE_COMMON_SERVICE = "CommonService";
  // 三维服务
  public static final String SERVICE_TYPE_REAL_SPACE_SERVICE = "RealSpaceService";
  // 瓦片服务
  public static final String SERVICE_TYPE_TILE_SERVICE = "TileService";
  //视频流服务
  public static final String SERVICE_TYPE_VIDEO_SERVICE = "VideoService";
  //多维数据服务
  public static final String SERVICE_TYPE_MULTI_DIM_DATA = "MultiDimDataService";

  /**
   * 大数据流服务类型
   */
  public static final String SERVICE_TYPE_DATA_FLOW_SERVICE = "dataflow";
  /**
   * 大数据地理编码服务类型
   */
  public static final String SERVICE_TYPE_GEO_CODING_SERVICE = "geocoding";

  /**
   * 服务类型（自管和托管数据库）
   */
  public static final String SERVICE_TYPE_SELF_MANAGEMENT_DB = "SelfManagementDb";
  public static final String SERVICE_TYPE_CUSTODY_MANAGEMENT_DB = "CustodyManagementDb";

  /**
   * 自定义Header
   */
  public static final String HTTP_HEADER_CLIENT = "client";
  public static final String HTTP_HEADER_USERID = "userId";
  public static final String HTTP_HEADER_SRC_HOST = "SrcHost";
  // public static final String HTTP_HEADER_SERVICE_ROUTE = "X-SERVICE-ROUTE";
  public static final String HTTP_HEADER_SERVICE_NAME = "serviceName";
  public static final String HTTP_HEADER_SERVICE_SOURCE_TYPE = "serviceSourceType";
  public static final String HTTP_HEADER_SERVICE_TYPE = "serviceType";
  public static final String HTTP_HEADER_SERVICE_OWNER = "serviceOwner";

  /**
   * 匿名用户标识
   */
  public static final String ANONYMOUS_USER = "anonymous";

  /**
   * 默认的管理员账户
   */
  public static final String ADMIN_ACCOUNT = "root";

  /**
   * 当用桌面端发服务时，设置发布者的用户名和cid
   */
  public static final String DESKTOP_ADMIN_ACCOUNT = "desktop";
  public static final Integer DESKTOP_ADMIN_CID = 0;

  /**
   * 默认的管理员账户（用户名cname）
   */
  public static final String ADMIN_ACCOUNT_CNAME = "系统管理员";

  /**
   * 指定负载均衡目标节点的参数名 - 针对微服务组件之间
   */
  public static final String LB_TARGET_PARAM_2 = "X-SERVICE-ROUTE";

  /**
   * 端口有效范围
   */
  public static final Integer PORT_MINIMUM = 1;

  public static final Integer PORT_MAXIMUM = 65535;

  /**
   * gis-service端口
   */
  public static final Integer GIS_SERVICE_PORT = 8699;

  /**
   * 节点类型：1：网关（集群）节点
   */
  public static final Integer NODE_TYPE_API_GATEWAY = 1;

  /**
   * 节点类型：2：gis-service节点
   */
  public static final Integer NODE_TYPE_GIS_SERVICE = 2;

  /**
   * 节点类型：3：大数据节点
   */
  public static final Integer NODE_TYPE_BIG_DATA = 3;

  /**
   * redis中的服务相关的hash名
   */
  public static final String SERVICES_OWNER_HASH_NAME = String.format("services%sowner",
      GlobalConstants.REDIS_KEY_SEPERATOR);
  public static final String SERVICES_3RD_PARTY_HASH_NAME = String.format("services%s3rd_party",
      GlobalConstants.REDIS_KEY_SEPERATOR);
  public static final String SERVICES_3RD_PARTY_OBJ = "services" + GlobalConstants.REDIS_KEY_SEPERATOR
      + "3rd_party_obj";
  // 托管数据入库状态
  public static final String CUSTODY_DB_STATUS = String.format("datas%scustody_db_status",
      GlobalConstants.REDIS_KEY_SEPERATOR);

  /**
   * 数据分类缓存key
   */
  public static final String DATA_CATEGORY_CACHE_KEY = "data_category";

  /**
   * 关系型数据库
   */
  public static final Integer SQL_TYPE_SQL = 1;

  /**
   * 非关系型数据库
   */
  public static final Integer SQL_TYPE_NO_SQL = 2;

  // 《KQGIS Server 8.1 服务接口指南-20200911》定义的数据源类型dbType
  public static final int DBTYPE_ORACLE_SDE = 0;
  public static final int DBTYPE_SQLSERVER_SDE = 1;
  public static final int DBTYPE_DB2_SDE = 2;
  public static final int DBTYPE_ACCESS_SDE = 3;
  public static final int DBTYPE_ORACLE_KQ = 4;
  public static final int DBTYPE_SQLSERVER_KQ = 5;
  public static final int DBTYPE_ACCESS_KQ = 6;
  // 2.0版本开始，不再使用，用DBTYPE_ORACLE_KQ代替
  public static final int DBTYPE_ORACLE_KQ2 = 7;
  // 2.0版本开始，不再使用，用DBTYPE_SQLSERVER_KQ代替
  public static final int DBTYPE_SQLSERVER_KQ2 = 8;
  // 2.0版本开始，不再使用，用DBTYPE_ACCESS_KQ代替
  public static final int DBTYPE_ACCESS_KQ2 = 9;
  // KqSDE for PostgreSQL
  public static final int DBTYPE_PG_KQ = 10;
  // KqSDE for Highgo
  public static final int DBTYPE_HIGHGO_KQ = 11;
  // KqSDE for DM 达梦数据库
  public static final int DBTYPE_DM_KQ = 12;
  // KqSDE for MySQL
  public static final int DBTYPE_MYSQL_KQ = 13;
  // KqSDE for KingBase ODBC
  public static final int DBTYPE_KINGBASE_ODBC_KQ = 14;
  // SHP文件
  public static final int DBTYPE_SHP_KQ = 15;
  // KqSDE for SpatialDB
  public static final int DBTYPE_SPATIAL_KQ = 16;

  // KqSDE for KingBase
  public static final int DBTYPE_KINGBASE_KQ = 17;
  // OGR数据源：包括ESRI Shapefile,CSV、GeoJSON、KML、OpenFileGDB、SVG、MapInfo File
  public static final int DBTYPE_OGRFILE = 21;
  public static final int DBTYPE_OPENFILEGDB = 31;
  // KqSDE专用数据类型
  public static final int DBTYPE_KQSDE_PRIVATE = 40;
  // 原生pg数据库
  public static final int DBTYPE_POSTGIS = 41;
  // KqSDE for GDB
  public static final int DBTYPE_GDB_KQ = 81;
  public static final int DBTYPE_MONGODB_KQ = 91;

  /**
   * 数据存储类型
   */
  // 自管数据库
  public static final int SELF_MANAGEMENT_DB = 1;
  // 托管数据库
  public static final int CUSTODY_DB = 2;
  // 共享目录
  public static final int SHARED_DIRECTORY = 3;

  /**
   * 地图数据源类型：上传KQMD文件 （已删除）
   */
  public static final int DATASOURCE_TYPE_KQMD_FILE = 1;

  /**
   * 地图数据源类型：选择已有数据源
   */
  public static final int DATASOURCE_TYPE_DATASOURCE = 2;

  /**
   * 地图数据源类型：上传KQMDX文件
   */
  public static final int DATASOURCE_TYPE_KQMDX_FILE = 3;

  /**
   * 地图数据源类型：上传GEOJSON文件
   */
  public static final int DATASOURCE_TYPE_GEOJSON_FILE = 4;

  /**
   * 上传 KQDATA 文件
   */
  public static final int DATASOURCE_TYPE_KQDATA_FILE = 5;

  /**
   * 上传 KQ3D 文件
   */
  public static final int DATASOURCE_TYPE_KQ3D_FILE = 6;

  /**
   * 上传 NETWORK 文件
   */
  public static final int DATASOURCE_TYPE_NETWORK_FILE = 7;

  /**
   * 上传 KQPLOTTING 文件
   */
  public static final int DATASOURCE_TYPE_KQPLOTTING_FILE = 8;

  /**
   * 上传 KQIMAGE 文件
   */
  public static final int DATASOURCE_TYPE_KQIMAGE_FILE = 9;

  /**
   * 上传 kqtile 文件
   */
  public static final int DATASOURCE_TYPE_KQTILE_FILE = 10;

  /**
   * 授权类型
   */
  public static final String GRANT_TYPE_ROOT = "Root";
  public static final String GRANT_TYPE_OWNER = "Owner";

  // 对应 kq_user_resource_permission 表的 grant_type 字段
  public static final String GRANT_TYPE_PUBLIC = "Public";
  public static final String GRANT_TYPE_PRIVATE = "Private";
  public static final String GRANT_TYPE_GROUP = "Group";
  public static final String GRANT_TYPE_PERSONAL = "Personal";
  public static final String GRANT_TYPE_ANONYMOUS = "Anonymous";
  public static final String GRANT_ID_PUBLIC = "/**";

  /**
   * 权限详情的 key 值
   */
  public static final String PERMISSION_NAME_SERVICE_SEARCH = "search";
  public static final String PERMISSION_NAME_SERVICE_BROWSE = "browse";
  public static final String PERMISSION_NAME_SERVICE_EDIT = "edit";

  // 可检索: 1；可浏览: 2；可编辑: 3
  public static final int PERMISSION_VALUE_SERVICE_SEARCH = 1;
  public static final int PERMISSION_VALUE_SERVICE_BROWSE = 2;
  public static final int PERMISSION_VALUE_SERVICE_EDIT = 3;

  /**
   * 任务类型--分布式切图任务
   */
  public static final int TASK_TYPE_DISTRIBUTE_CACHE = 1;

  /**
   * 任务状态--未完成
   */
  public static final int TASK_STATUS_UNFINISHED = 0;

  // 内部约定的token值, 用于自动化部署等
  public static final String TOKEN_ROOT = "__root_token__";

  // Key命名规范
  public static final String USER_TOKEN_KEY = "user:token:%s";       // token -> 用户信息
  public static final String USER_LOGIN_KEY = "user:login:%s";      // userId -> token
  public static final String USER_INFO_KEY = "user:info:%s";        // 用户详细信息
  public static final String USER_IDS_KEY = "user:ids";             // 所有用户ID集合
}
