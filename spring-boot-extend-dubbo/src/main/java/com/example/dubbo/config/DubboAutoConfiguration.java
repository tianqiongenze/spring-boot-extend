package com.example.dubbo.config;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.Filter;
import com.example.common.config.PluginConfigManager;
import com.example.common.constant.EnvironmentManager;
import com.example.common.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.util.Set;

import static com.example.common.exception.BaseExceotionEnum.INITIALIZE_FILTER_ERROR;

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

        registerProviderCondfigBean();
        regisgerConsumerConfigBean();
        registerAnnodationBean();
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.env = (ConfigurableEnvironment) environment;
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
                throw new BaseException(e, INITIALIZE_FILTER_ERROR.getCode(), INITIALIZE_FILTER_ERROR.getMessage(), false);
            }
        });
    }


    private void registerAnnodationBean() {
    }

    private void regisgerConsumerConfigBean() {
    }


    private void registerProviderCondfigBean() {
    }

}
