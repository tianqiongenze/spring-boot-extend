package com.example.dubbo.config;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import com.alibaba.dubbo.rpc.Filter;
import com.example.common.config.PluginConfigManager;
import com.example.common.constant.EnvironmentManager;
import com.example.common.exception.BaseExceotionEnum;
import com.example.common.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @version 1.0
 * @ClassName DubboConfiguration
 * @Description TODO描述
 * @Author mingj
 * @Date 2019/12/16 23:17
 **/
public class DubboAutoConfiguration implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {

    private ConfigurableEnvironment env;
    private Set<String> filterList;
    private  ApplicationContext applicationContext;


    private static final Logger logger = LoggerFactory.getLogger(DubboAutoConfiguration.class);


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        //初始化过滤器
        initFilter();

        //加载配置参数
        ApplicationConfig config = new ApplicationConfig();
        config.setName(EnvironmentManager.getAppid());
        config.setLogger(EnvironmentManager.getProperty(EnvironmentManager.DUBBO_LOGGER));

        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName(EnvironmentManager.getProperty(EnvironmentManager.DUBBO_PROTOCOL));
        protocolConfig.setHost(EnvironmentManager.getProperty(EnvironmentManager.DUBBO_HOST));
        protocolConfig.setPort(Integer.valueOf(EnvironmentManager.getProperty(EnvironmentManager.DUBBO_HOST, "20880")));

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol(EnvironmentManager.getProperty(EnvironmentManager.DUBBO_PROTOCOL, "zookeeper"));
        registryConfig.setAddress(EnvironmentManager.getProperty(EnvironmentManager.DUBBO_REGISTRY_ADDRESS));
        registryConfig.setRegister(true);
        registryConfig.setSubscribe(true);

        registerProviderCondfigBean(config, protocolConfig, registryConfig, beanDefinitionRegistry);
        regisgerConsumerConfigBean(config, registryConfig, beanDefinitionRegistry);
        registerAnnodationBean(beanDefinitionRegistry);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.env = (ConfigurableEnvironment) environment;
    }

    /**
    *@Description 服务端过滤器
    *@Param []
    *@Author mingj
    *@Date 2019/12/22 17:03
    *@Return java.util.Set<java.lang.String>
    **/
    private Set<String> getProviderFilter() {
        Set<String> filter = new HashSet<>();
        for (String str : filterList) {
            if (str.contains("all") || str.contains("provider")) {
                filter.add(str.substring(str.lastIndexOf(".") + 1));
            }
        }
        return filter;
    }

    /**
    *@Description 消费端过滤器
    *@Param [ ]
    *@Author mingj
    *@Date 2019/12/22 17:04
    *@Return java.util.Set<java.lang.String>
    **/
    private Set<String> getConsumerFilter() {
        Set<String> filter = new HashSet<>();
        for (String str : filterList) {
            if (str.contains("all") || str.contains("consumer")) {
                filter.add(str.substring(str.lastIndexOf(".") + 1));
            }
        }
        return filter;
    }

    /**
    *@Description 初始化Filter
    *@Param []
    *@Author mingj
    *@Date 2019/12/17 22:55
    *@Return void
    **/
    private void initFilter(){
        Set<String> propertyValueSet = PluginConfigManager.getPropertyValueSet(Filter.class.getName());
        filterList.addAll(propertyValueSet);
        propertyValueSet.forEach( value -> {
            try {
                Filter filter = (Filter) Class.forName(value).newInstance();
                ExtensionLoader.getExtensionLoader(Filter.class).addExtension(value.substring(value.lastIndexOf(".") + 1), filter.getClass());
            } catch (Exception e) {
                throw new BaseException(e, BaseExceotionEnum.INITIALIZE_FILTER_ERROR.getCode(), BaseExceotionEnum.INITIALIZE_FILTER_ERROR.getMessage(), BaseExceotionEnum.INITIALIZE_FILTER_ERROR.getStatus());
            }
        });
    }

    /**
    *@Description 注册
    *@Param [beanDefinitionRegistry]
    *@Author mingj
    *@Date 2019/12/22 17:12
    *@Return void
    **/
    private void registerAnnodationBean(BeanDefinitionRegistry beanDefinitionRegistry) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(AnnotationBean.class);
        builder.addPropertyValue("annotationPackage", EnvironmentManager.getProperty(EnvironmentManager.DUBBO_SCAN_PACKAGE_NAME));
        builder.addPropertyValue("applicationContext", applicationContext);
        beanDefinitionRegistry.registerBeanDefinition("annotationBean", builder.getRawBeanDefinition());
    }
    
    /**
    *@Description 消费端注册
    *@Param [config, registryConfig, beanDefinitionRegistry]
    *@Author mingj
    *@Date 2019/12/22 17:12
    *@Return void
    **/
    private void regisgerConsumerConfigBean(ApplicationConfig config, RegistryConfig registryConfig, BeanDefinitionRegistry beanDefinitionRegistry) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ConsumerConfig.class);
        builder.addPropertyValue("timeout", Integer.valueOf(EnvironmentManager.getProperty(EnvironmentManager.DUBBO_TIMEOUT)));
        builder.addPropertyValue("retries", Integer.valueOf(EnvironmentManager.getProperty(EnvironmentManager.DUBBO_RETRIES)));
        builder.addPropertyValue("application", config);
        builder.addPropertyValue("registries", Collections.singletonList(registryConfig));
        builder.addPropertyValue("filter", String.join(",", getConsumerFilter()));
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
        builder.addPropertyValue("timeout", Integer.valueOf(EnvironmentManager.getProperty(EnvironmentManager.DUBBO_TIMEOUT)));
        builder.addPropertyValue("retries", Integer.valueOf(EnvironmentManager.getProperty(EnvironmentManager.DUBBO_RETRIES)));
        builder.addPropertyValue("delay", Integer.valueOf(EnvironmentManager.getProperty(EnvironmentManager.DUBBO_DELAY)));
        builder.addPropertyValue("application", config);
        builder.addPropertyValue("protocols", Collections.singletonList(protocolConfig));
        builder.addPropertyValue("registries", Collections.singletonList(registryConfig));
        builder.addPropertyValue("filter", String.join(",", getProviderFilter()));
        beanDefinitionRegistry.registerBeanDefinition("providerConfig", builder.getRawBeanDefinition());
    }

}
