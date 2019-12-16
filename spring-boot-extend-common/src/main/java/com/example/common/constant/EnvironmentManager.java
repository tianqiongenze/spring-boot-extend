package com.example.common.constant;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @version 1.0
 * @ClassName EnvironmentManager
 * @Description 环境参数配置类
 * @Author mingj
 * @Date 2019/9/28 21:43
 **/
public class EnvironmentManager {

    //mybatis配置
    public static final String MYBATIS_CONFIG_NAME = "mybatis.config.name";
    public static final String MYBATIS_CONFIG_BASEPACKAGE = "mybatis.config.basePackage";
    public static final String MYBATIS_CONFIG_DRIVERCLASSNAME = "mybatis.config.driverClassName";
    public static final String MYBATIS_CONFIG_JDBCURL = "mybatis.config.jdbcUrl";
    public static final String MYBATIS_CONFIG_USERNAME = "mybatis.config.username";
    public static final String MYBATIS_CONFIG_PASSWORD = "mybatis.config.password";
    public static final String MYBATIS_CONFIG_READONLY = "mybatis.config.readOnly";
    public static final String MYBATIS_CONFIG_CONNECTIONTIMEOUT = "mybatis.config.connectionTimeout";
    public static final String MYBATIS_CONFIG_IDLETIMEOUT = "mybatis.config.idleTimeout";
    public static final String MYBATIS_CONFIG_MAXLIFETIME = "mybatis.config.maxLifetime";
    public static final String MYBATIS_CONFIG_MAXIMUMPOOLSIZE = "mybatis.config.maximumPoolSize";
    public static final String MYBATIS_CONFIG_MAPPERLOCATIONS = "mybatis.config.mapperLocations";
    public static final String MYBATIS_CONFIG_MAPPER_ANNOTATION_CLASS = "mybatis.config.mapperAnnotationClass";
    public static final String MYBATIS_CONFIG_TYPEALIASEPACKAGE = "mybatis.config.typeAliasesPackage";
    public static final String MYBATIS_CONFIG_MARKERINTERFACE = "mybatis.config.markerInterface";
    public static final String MYBATIS_CONFIG_DEFAULTSTATEMENTTIMEOUT = "mybatis.config.defaultStatementTimeoutInSecond";
    public static final String MYBATIS_CONFIG_CONFIGLOCATION = "mybatis.config.configLocation";
    public static final String MYBATIS_CONFIG_MAPUNDERSCORETOCAMELCASE = "mybatis.config.mapUnderscoreToCamelCase";
    public static final String MYBATIS_CONFIG_CONNECTIONINITSQL = "mybatis.configs.connectionInitSql";
    //mybatis多库配置
    public static final String MYBATIS_CONFIGS_NAME = "mybatis.configs[%s].name";
    public static final String MYBATIS_CONFIGS_BASEPACKAGE = "mybatis.configs[%s].basePackage";
    public static final String MYBATIS_CONFIGS_DRIVERCLASSNAME = "mybatis.configs[%s].driverClassName";
    public static final String MYBATIS_CONFIGS_JDBCURL = "mybatis.configs[%s].jdbcUrl";
    public static final String MYBATIS_CONFIGS_USERNAME = "mybatis.configs[%s].username";
    public static final String MYBATIS_CONFIGS_PASSWORD = "mybatis.configs[%s].password";
    public static final String MYBATIS_CONFIGS_READONLY = "mybatis.configs[%s].readOnly";
    public static final String MYBATIS_CONFIGS_CONNECTIONTIMEOUT = "mybatis.configs[%s].connectionTimeout";
    public static final String MYBATIS_CONFIGS_IDLETIMEOUT = "mybatis.configs[%s].idleTimeout";
    public static final String MYBATIS_CONFIGS_MAXLIFETIME = "mybatis.configs[%s].maxLifetime";
    public static final String MYBATIS_CONFIGS_MAXIMUMPOOLSIZE = "mybatis.configs[%s].maximumPoolSize";
    public static final String MYBATIS_CONFIGS_MAPPERLOCATIONS = "mybatis.configs[%s].mapperLocations";
    public static final String MYBATIS_CONFIGS_TYPEALIASEPACKAGE = "mybatis.configs[%s].typeAliasesPackage";
    public static final String MYBATIS_CONFIGS_MARKERINTERFACE = "mybatis.configs[%s].markerInterface";
    public static final String MYBATIS_CONFIGS_DEFAULTSTATEMENTTIMEOUT = "mybatis.config[%s].defaultStatementTimeoutInSecond";
    public static final String MYBATIS_CONFIGS_CONFIGLOCATION = "mybatis.configs[%s].configLocation";
    public static final String MYBATIS_CONFIGS_MAPUNDERSCORETOCAMELCASE = "mybatis.configs[%s].mapUnderscoreToCamelCase";
    public static final String MYBATIS_CONFIGS_CONNECTIONINITSQL = "mybatis.configs[%s].connectionInitSql";

    //mongodb的配置key
    public static final String MONGODB_CONFIG_NAME = "mongodb.config.name";
    public static final String MONGODB_CONFIG_REPOSITORIESENABLED = "mongodb.config.repositoriesEnabled";
    public static final String MONGODB_CONFIG_REPOSITORIESLOCATIONS = "mongodb.config.repositoriesLocations";
    public static final String MONGODB_CONFIG_REPOSITORIESAUTHENTICATIONDATABASE = "mongodb.config.repositoriesAuthenticationDatabase";
    public static final String MONGODB_CONFIG_MONGODB_URI = "mongodb.config.mongodbUri";
    public static final String MONGODB_CONFIG_MINCONNECTIONSPERHOST = "mongodb.config.minConnectionsPerHost";
    public static final String MONGODB_CONFIG_CONNECTIONSPERHOST = "mongodb.config.connectionsPerHost";
    public static final String MONGODB_CONFIG_CONNECTTIMEOUT = "mongodb.config.connectTimeout";
    public static final String MONGODB_CONFIG_THREADSALLOWEDTOBLOCKFORCONNECTIONMULTIPLIER = "mongodb.config.threadsAllowedToBlockForConnectionMultiplier";
    public static final String MONGODB_CONFIG_MAXCONNECTIONIDLETIME = "mongodb.config.maxConnectionIdleTime";
    public static final String MONGODB_CONFIG_MAXCONNECTIONLIFETIME = "mongodb.config.maxConnectionLifeTime";
    public static final String MONGODB_CONFIG_SOCKETTIMEOUT = "mongodb.config.socketTimeout";
    public static final String MONGODB_CONFIG_MAXWAITTIME = "mongodb.config.maxWaitTime";
    public static final String MONGODB_CONFIG_SOCKETKEEPALIVE = "mongodb.config.socketKeepAlive";
    public static final String MONGODB_CONFIG_SERVERSELECTIONTIMEOUT = "mongodb.config.serverSelectionTimeout";

    //mongodb的配置key(多库配置)
    public static final String MONGODB_CONFIGS_NAME = "mongodb.configs[%s].name";
    public static final String MONGODB_CONFIGS_REPOSITORIESENABLED = "mongodb.configs[%s].repositoriesEnabled";
    public static final String MONGODB_CONFIGS_REPOSITORIESLOCATIONS = "mongodb.configs[%s].repositoriesLocations";
    public static final String MONGODB_CONFIGS_REPOSITORIESAUTHENTICATIONDATABASE = "mongodb.configs[%s].repositoriesAuthenticationDatabase";
    public static final String MONGODB_CONFIGS_MONGODB_URI = "mongodb.configs[%s].mongodbUri";
    public static final String MONGODB_CONFIGS_MINCONNECTIONSPERHOST = "mongodb.configs[%s].minConnectionsPerHost";
    public static final String MONGODB_CONFIGS_CONNECTIONSPERHOST = "mongodb.configs[%s].connectionsPerHost";
    public static final String MONGODB_CONFIGS_CONNECTTIMEOUT = "mongodb.configs[%s].connectTimeout";
    public static final String MONGODB_CONFIGS_THREADSALLOWEDTOBLOCKFORCONNECTIONMULTIPLIER = "mongodb.configs[%s].threadsAllowedToBlockForConnectionMultiplier";
    public static final String MONGODB_CONFIGS_MAXCONNECTIONIDLETIME = "mongodb.configs[%s].maxConnectionIdleTime";
    public static final String MONGODB_CONFIGS_MAXCONNECTIONLIFETIME = "mongodb.configs[%s].maxConnectionLifeTime";
    public static final String MONGODB_CONFIGS_SOCKETTIMEOUT = "mongodb.configs[%s].socketTimeout";
    public static final String MONGODB_CONFIGS_MAXWAITTIME = "mongodb.configs[%s].maxWaitTime";
    public static final String MONGODB_CONFIGS_SOCKETKEEPALIVE = "mongodb.configs[%s].socketKeepAlive";
    public static final String MONGODB_CONFIGS_SERVERSELECTIONTIMEOUT = "mongodb.configs[%s].serverSelectionTimeout";

    //mongodb的默认配置
    public static final Integer MONGODB_DEFAULT_CONFIG_MINCONNECTIONSPERHOST = 30;
    public static final Integer MONGODB_DEFAULT_CONFIG_CONNECTIONSPERHOST = 50;
    public static final Integer MONGODB_DEFAULT_CONFIG_CONNECTTIMEOUT = 10 * 1000;
    public static final Integer MONGODB_DEFAULT_CONFIG_THREADSALLOWEDTOBLOCKFORCONNECTIONMULTIPLIER = 10;
    public static final Integer MONGODB_DEFAULT_CONFIG_MAXCONNECTIONIDLETIME = 60 * 60 * 1000;
    public static final Integer MONGODB_DEFAULT_CONFIG_MAXCONNECTIONLIFETIME = 6 * 60 * 60 * 1000;
    public static final Integer MONGODB_DEFAULT_CONFIG_SOCKETTIMEOUT = 10 * 1000;
    public static final Integer MONGODB_DEFAULT_CONFIG_MAXWAITTIME = 10 * 1000;
    public static final Boolean MONGODB_DEFAULT_CONFIG_SOCKETKEEPALIVE = true;
    public static final Integer MONGODB_DEFAULT_CONFIG_SERVERSELECTIONTIMEOUT = 1000 * 300;
    public static final Boolean MONGODB_DEFAULT_CONFIG_REPOSITORIESENABLED = true;

    //dubbo服务配置
    public static final String DUBBO_PROTOCOL = "dubbo.protocol";
    public static final String DUBBO_PORT = "dubbo.port";
    public static final String DUBBO_LOGGER = "dubbo.logger";
    public static final String DUBBO_HOST = "dubbo.host";
    public static final String DUBBO_TIMEOUT = "dubbo.timeout";
    public static final String DUBBO_RETRIES = "dubbo.retries";
    public static final String DUBBO_DELAY = "dubbo.delay";
    public static final String DUBBO_REGISTRY_PROTOCOL = "dubbo.registryProtocol";
    public static final String DUBBO_REGISTRY_ADDRESS = "dubbo.registryAddress";
    public static final String DUBBO_SCAN_PACKAGE_NAME = "dubbo.scanPackageName";
    public static final String DUBBO_ENV_CONFIG_NAME = "dubbo.env.name";
    public static final String DUBBO_CAT_SERVER_APPLICATION_CONFIG_NAME = "serverApplicationName";


    private static Properties properties;
    private static final Map<String, Properties> PROPERTIES_MAP = new HashMap<>();

    /**
    *@Description 
    *@Param [key, value]
    *@Author mingj
    *@Date 2019/12/16 23:25
    *@Return void
    **/
    public static void setProperty(String key, String value) {
        if (properties == null) {
            properties = PROPERTIES_MAP.get(getEnv());
            if (properties == null) {
                properties = new Properties();
            }
        }
        properties.setProperty(key, value);
    }

    /**
    *@Description 获取环境变量
    *@Param []
    *@Author mingj
    *@Date 2019/12/16 23:25
    *@Return java.lang.String
    **/
    private static String getEnv() {
        final String env = System.getProperty("env");
        return env == null ? "dev" : env;
    }
}
