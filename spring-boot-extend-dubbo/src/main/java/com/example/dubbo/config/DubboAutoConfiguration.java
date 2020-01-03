package com.example.dubbo.config;

import com.alibaba.dubbo.config.*;
import com.example.common.constant.EnvironmentManager;
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

import java.util.Collections;

/**
 * @version 1.0
 * @ClassName DubboConfiguration
 * @Description TODO描述
 * @Author mingj
 * @Date 2019/12/16 23:17
 **/
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class, DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class DubboAutoConfiguration implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {

    private ConfigurableEnvironment env;

    private static final Logger logger = LoggerFactory.getLogger(DubboAutoConfiguration.class);


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        //加载配置参数
        ApplicationConfig config = new ApplicationConfig();
        config.setName(EnvironmentManager.getAppid());
        config.setLogger(getProperty(EnvironmentManager.DUBBO_LOGGER));

        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName(getProperty(EnvironmentManager.DUBBO_PROTOCOL));
        protocolConfig.setHost(getProperty(EnvironmentManager.DUBBO_HOST));
        protocolConfig.setPort(Integer.valueOf(getProperty(EnvironmentManager.DUBBO_PORT, "20880")));

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol(getProperty(EnvironmentManager.DUBBO_PROTOCOL));
        registryConfig.setAddress(getProperty(EnvironmentManager.DUBBO_REGISTRY_ADDRESS));
        registryConfig.setRegister(true);
        registryConfig.setSubscribe(true);

        registerProviderCondfigBean(config, protocolConfig, registryConfig, beanDefinitionRegistry);
        registerConsumerConfigBean(config, registryConfig, beanDefinitionRegistry);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.env = (ConfigurableEnvironment) environment;
    }

    /**
    *@Description 消费端注册
    *@Param [config, registryConfig, beanDefinitionRegistry]
    *@Author mingj
    *@Date 2019/12/22 17:12
    *@Return void
    **/
    private void registerConsumerConfigBean(ApplicationConfig config, RegistryConfig registryConfig, BeanDefinitionRegistry beanDefinitionRegistry) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ConsumerConfig.class);
        builder.addPropertyValue("timeout", Integer.valueOf(getProperty(EnvironmentManager.DUBBO_TIMEOUT)));
        builder.addPropertyValue("retries", Integer.valueOf(getProperty(EnvironmentManager.DUBBO_RETRIES)));
        builder.addPropertyValue("application", config);
        builder.addPropertyValue("registries", Collections.singletonList(registryConfig));
        beanDefinitionRegistry.registerBeanDefinition("consumerConfig", builder.getRawBeanDefinition());
    }

    /**
    *@Description 服务端注册
    *@Param [config, protocolConfig, registryConfig, beanDefinitionRegistry]
    *@Author mingj
    *@Date 2019/12/22 17:12
    *@Return void
    **/
    private void registerProviderCondfigBean(ApplicationConfig config, ProtocolConfig protocolConfig, RegistryConfig registryConfig, BeanDefinitionRegistry beanDefinitionRegistry) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ProviderConfig.class);
        builder.addPropertyValue("timeout", Integer.valueOf(getProperty(EnvironmentManager.DUBBO_TIMEOUT)));
        builder.addPropertyValue("retries", Integer.valueOf(getProperty(EnvironmentManager.DUBBO_RETRIES)));
        builder.addPropertyValue("delay", Integer.valueOf(getProperty(EnvironmentManager.DUBBO_DELAY)));
        builder.addPropertyValue("application", config);
        builder.addPropertyValue("protocols", Collections.singletonList(protocolConfig));
        builder.addPropertyValue("registries", Collections.singletonList(registryConfig));
        beanDefinitionRegistry.registerBeanDefinition("providerConfig", builder.getRawBeanDefinition());
    }

    /**
    *@Description 读取配置参数
    *@Param [key]
    *@Author mingj
    *@Date 2019/12/25 20:01
    *@Return java.lang.String
    **/
    private String getProperty(String key){
        return EnvironmentManager.getProperty(env, key);
    }

    /**
    *@Description 携带默认值
    *@Param [key, defaultValue]
    *@Author mingj
    *@Date 2019/12/25 20:57
    *@Return java.lang.String
    **/
    private String getProperty(String key, String defaultValue){
        return EnvironmentManager.getProperty(env, key, defaultValue);
    }
}
