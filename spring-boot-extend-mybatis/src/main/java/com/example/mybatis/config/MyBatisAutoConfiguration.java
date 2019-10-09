package com.example.mybatis.config;

import com.example.common.constant.EnvironmentManager;
import com.example.mybatis.properties.MyBatisConfigurationProperties;
import com.example.mybatis.utils.MyBatisConfigurationLoadUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @version 1.0
 * @ClassName MyBatisAutoConfiguration
 * @Description Mybatis的配置类，排除spring boot的自动配置选择器
 * @Author mingj
 * @Date 2019/9/22 23:16
 **/
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class, DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class MyBatisAutoConfiguration implements BeanDefinitionRegistryPostProcessor,EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(MyBatisAutoConfiguration.class);

    private ConfigurableEnvironment env;
    private MyBatisConfigurationProperties config;
    private List<MyBatisConfigurationProperties> configs;



    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanFactory) throws BeansException {
        //1.初始化配置参数
        initConfig();
        if (config != null) {
            registerBean(config, beanFactory);
        } else if (configs != null) {
            for (MyBatisConfigurationProperties config : configs) {
                registerBean(config, beanFactory);
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.env = (ConfigurableEnvironment) environment;
    }

    /**
    *@Description 初始化配置，读取mybatis的数据库配置参数
    *@Param []
    *@Author mingj
    *@Date 2019/9/28 21:37
    *@Return void
    **/
    private void initConfig(){
        if (!StringUtils.isEmpty(env.getProperty(EnvironmentManager.MYBATIS_CONFIG_NAME))) {
            config = MyBatisConfigurationLoadUtil.loadSingleMyBatisConfiguration(env);
        } else {
            configs = MyBatisConfigurationLoadUtil.loadMultipleMyBatisConfiguration(env);
        }
    }

    /**
    *@Description 注册beans
    *@Param [config, beanFactory]
    *@Author mingj
    *@Date 2019/9/29 15:30
    *@Return void
    **/
    private void registerBean(MyBatisConfigurationProperties config, BeanDefinitionRegistry beanFactory) {

        String dataSourceName = config.getName() + "DataSource";
        String sessionFactoryName = config.getName() + "SessionFactory";
        String mapperScannerConfigurerName = config.getName() + "MapperScannerConfigurer";
        String transactionManagerName = config.getName() + "TransactionManager";
        String transactionTemplateName = config.getName() + "TransactionTemplate";
        registerDataSourceBeanDefinitionBuilder(dataSourceName, beanFactory, config);
        registerSessionFactoryDefinitionBuilder(sessionFactoryName, beanFactory, config, dataSourceName);
        registerMapperScannerDefinitionBuilder(mapperScannerConfigurerName, beanFactory, config, sessionFactoryName);
        registerTransactionManagerDefinitionBuilder(transactionManagerName, beanFactory, dataSourceName);
        registerTransactionTemplateDefinitionBuilder(transactionTemplateName, beanFactory, transactionManagerName);
    }

    /**
    *@Description 注册数据源
    *@Param [dataSourceName, beanFactory, config]
    *@Author mingj
    *@Date 2019/9/29 15:31
    *@Return void
    **/
    private void registerDataSourceBeanDefinitionBuilder(String dataSourceName, BeanDefinitionRegistry beanFactory, MyBatisConfigurationProperties config) {
        HikariConfig hikariConfig = MyBatisConfigurationLoadUtil.createDataSource(config);
        BeanDefinitionBuilder dataSourceDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(HikariDataSource.class);
        dataSourceDefinitionBuilder.addConstructorArgValue(hikariConfig);
        beanFactory.registerBeanDefinition(dataSourceName, dataSourceDefinitionBuilder.getRawBeanDefinition());
    }
}
