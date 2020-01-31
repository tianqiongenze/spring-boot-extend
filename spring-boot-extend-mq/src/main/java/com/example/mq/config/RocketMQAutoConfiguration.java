package com.example.mq.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

/**
 * @version 1.0
 * @ClassName RocketMQAutoConfiguration
 * @Description MQ自动配置
 * @Author mingj
 * @Date 2020/1/31 22:13
 **/
public class RocketMQAutoConfiguration implements EnvironmentAware, BeanDefinitionRegistryPostProcessor {

    private ConfigurableEnvironment env;


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // 注册RocketMQListenerInitialization
        registerRocketMQListenerInitialization(registry);
        // 注册消息模板RocketMQTemplate
        registerRocketMQTemplate(registry);
    }

    /**
    *@Description 注册消息模板RocketMQTemplate
    *@Param [registry]
    *@Author mingj
    *@Date 2020/1/31 22:28
    *@Return void
    **/
    private void registerRocketMQTemplate(BeanDefinitionRegistry registry) {
    }

    /**
    *@Description 注册RocketMQListenerInitialization
    *@Param [registry]
    *@Author mingj
    *@Date 2020/1/31 22:27
    *@Return void
    **/
    private void registerRocketMQListenerInitialization(BeanDefinitionRegistry registry) {

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.env = (ConfigurableEnvironment) environment;
    }
}
