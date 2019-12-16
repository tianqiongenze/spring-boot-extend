package com.example.dubbo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * @version 1.0
 * @ClassName DubboConfiguration
 * @Description TODO描述
 * @Author mingj
 * @Date 2019/12/16 23:17
 **/
public class DubboAutoConfiguration implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(DubboAutoConfiguration.class);


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    @Override
    public void setEnvironment(Environment environment) {

    }
}
