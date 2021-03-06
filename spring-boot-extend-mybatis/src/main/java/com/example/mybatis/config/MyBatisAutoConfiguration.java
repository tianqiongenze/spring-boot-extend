package com.example.mybatis.config;

import com.example.common.constant.EnvironmentManager;
import com.example.common.exception.BaseException;
import com.example.common.utils.ConfigurationLoadUtil;
import com.example.mybatis.properties.MyBatisConfigurationProperties;
import com.example.mybatis.utils.MyBatisConfigurationLoadUtil;
import com.example.common.config.PluginConfigManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
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
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
        if (!StringUtils.isEmpty(ConfigurationLoadUtil.getProperty(env, EnvironmentManager.MYBATIS_CONFIG_NAME))) {
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
    *@Description 注册事务模板
    *@Param [transactionTemplateName, beanFactory, transactionManagerName]
    *@Author mingj
    *@Date 2019/10/27 12:54
    *@Return void
    **/
    private void registerTransactionTemplateDefinitionBuilder(String transactionTemplateName, BeanDefinitionRegistry beanFactory, String transactionManagerName) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(TransactionTemplate.class);
        beanDefinitionBuilder.addPropertyReference("transactionManager", transactionManagerName);
        beanFactory.registerBeanDefinition(transactionTemplateName, beanDefinitionBuilder.getRawBeanDefinition());
    }

    /**
    *@Description 注册事务管理器
    *@Param [transactionManagerName, beanFactory, dataSourceName]
    *@Author mingj
    *@Date 2019/10/27 12:48
    *@Return void
    **/
    private void registerTransactionManagerDefinitionBuilder(String transactionManagerName, BeanDefinitionRegistry beanFactory, String dataSourceName) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(DataSourceTransactionManager.class);
        beanDefinitionBuilder.addPropertyReference("dataSource", dataSourceName);
        beanFactory.registerBeanDefinition(transactionManagerName, beanDefinitionBuilder.getRawBeanDefinition());
    }

    /**
    *@Description 注册Mapper
    *@Param [mapperScannerConfigurerName, beanFactory, config, sessionFactoryName]
    *@Author mingj
    *@Date 2019/10/27 12:15
    *@Return void
    **/
    private void registerMapperScannerDefinitionBuilder(String mapperScannerConfigurerName, BeanDefinitionRegistry beanFactory, MyBatisConfigurationProperties config, String sessionFactoryName) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(MapperScannerConfigurer.class);
        beanDefinitionBuilder.addPropertyValue("basePackage", config.getBasePackage());
        beanDefinitionBuilder.addPropertyReference("sqlSessionFactory", sessionFactoryName);
        if (config.getMarkerInterface() != null) {
            beanDefinitionBuilder.addPropertyValue("markerInterface", config.getMarkerInterface());
        }
        if (!StringUtils.isEmpty(config.getMapperAnnotationClass())) {
            try {
                beanDefinitionBuilder.addPropertyValue("annotationClass", Class.forName(config.getMapperAnnotationClass()));
            } catch (Exception e) {
                throw new RuntimeException("The Mapper Annotation Class [" + config.getMapperAnnotationClass() + "] Not Loading !!");
            }
        }
        beanFactory.registerBeanDefinition(mapperScannerConfigurerName, beanDefinitionBuilder.getRawBeanDefinition());
    }

    /**
    *@Description 注册SessionFactory
    *@Param [sessionFactoryName, beanFactory, config, dataSourceName]
    *@Author mingj
    *@Date 2019/10/20 22:46
    *@Return void
    **/
    private void registerSessionFactoryDefinitionBuilder(String sessionFactoryName, BeanDefinitionRegistry beanFactory, MyBatisConfigurationProperties config, String dataSourceName) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(SqlSessionFactoryBean.class);
        beanDefinitionBuilder.addPropertyValue("mapperLocations", resolveMapperLocations(config.getMapperLocations()));
        beanDefinitionBuilder.addPropertyValue("typeAliasesPackage", config.getTypeAliasesPackage());
        //判断configLocation和Configuration的互斥关系
        if (!StringUtils.isEmpty(config.getConfigLocation())) {
            //读取mybatis的全局配置文件
            beanDefinitionBuilder.addPropertyValue("configLocation", config.getConfigLocation());
        } else {
            Configuration configuration = new Configuration();
            if (config.getDefaultStatementTimeout() != null) {
                configuration.setDefaultStatementTimeout(config.getDefaultStatementTimeout());
            }
            if (config.getMapUnderscoreToCamelCase() != null){
                configuration.setMapUnderscoreToCamelCase(config.getMapUnderscoreToCamelCase());
            }
            beanDefinitionBuilder.addPropertyValue("configuration", configuration);
        }
        beanDefinitionBuilder.addPropertyReference("dataSource", dataSourceName);
        List<Interceptor> mybatisInterceptors = new ArrayList<>();
        Set<String> mybatisPlugins = PluginConfigManager.getPropertyValueSet("org.apache.ibatis.plugin.Interceptor");
        mybatisPlugins.forEach(mybatisPlugin -> {
            try {
                Class pluginClass = Class.forName(mybatisPlugin);
                Interceptor mybatisInterceptor = (Interceptor) pluginClass.newInstance();
                mybatisInterceptors.add(mybatisInterceptor);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                logger.error("load mybatis plugin error: ", e);
            }
        });

        beanDefinitionBuilder.addPropertyValue("plugins", mybatisInterceptors);
        beanFactory.registerBeanDefinition(sessionFactoryName, beanDefinitionBuilder.getRawBeanDefinition());

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

    /**
    *@Description 读取mapperLocations地址
    *@Param [mapperLocations]
    *@Author mingj
    *@Date 2019/10/20 23:45
    *@Return org.springframework.core.io.Resource[]
    **/
    private Resource[] resolveMapperLocations(String mapperLocations) {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<Resource> resources = new ArrayList<Resource>();
        if (mapperLocations != null) {
            String[] mapperLocationArray = mapperLocations.split(",");
            for (String mapperLocation : mapperLocationArray) {
                try {
                    Resource[] mappers = resourceResolver.getResources(mapperLocation);
                    resources.addAll(Arrays.asList(mappers));
                } catch (IOException e) {
                    throw new BaseException(e, "SYS000", "Mybatis接口文件读取失败", false);
                }
            }
        }
        return resources.toArray(new Resource[resources.size()]);
    }


}
