package com.example.common.config;

import com.example.common.properties.MyBatisAutoConfigurationProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @version 1.0
 * @ClassName MyBatisAutoConfiguration
 * @Description Mybatis的配置类，排除spring boot的自动配置选择器
 * @Author mingj
 * @Date 2019/9/22 23:16
 **/
@EnableConfigurationProperties({MyBatisAutoConfigurationProperties.class})
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class, DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class MyBatisAutoConfiguration {
}
