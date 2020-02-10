package com.example.mq.config;

import com.example.common.constant.EnvironmentManager;
import com.example.common.exception.BaseExceotionEnum;
import com.example.common.exception.BaseException;
import com.example.mq.plugin.RocketMQTemplate;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
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
    private static final Integer MIN_THREAD_NUM = 20;
    private static final Integer MAX_THREAD_NUM = 64;
    private static final Integer SEND_MESSAGE_TIME_OUT = 6000;


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
        String producerGroup = EnvironmentManager.getProperty(env, "rocketmq.producerGroup", EnvironmentManager.getAppid()+"ProducerGroup");
        String nameServerAddress = EnvironmentManager.getProperty(env, "rocketmq.nameServerAddress");
        int sendMsgTimeout = Integer.parseInt(EnvironmentManager.getProperty(env, "rocketmq.sendMsgTimeOut", SEND_MESSAGE_TIME_OUT+""));

        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setProducerGroup(producerGroup);
        producer.setSendMsgTimeout(sendMsgTimeout);
        producer.setNamesrvAddr(nameServerAddress);

        //begin the producer
        try {
            producer.start();
        } catch (MQClientException e) {
            throw new BaseException(e, BaseExceotionEnum.ROCKET_MQ_INIT_ERROR.getCode(), BaseExceotionEnum.ROCKET_MQ_INIT_ERROR.getMessage(), BaseExceotionEnum.ROCKET_MQ_INIT_ERROR.getStatus());
        }
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(RocketMQTemplate.class);
        builder.addPropertyValue("producer", producer);
        registry.registerBeanDefinition("rocketmqTemplate", builder.getRawBeanDefinition());
    }

    /**
    *@Description 注册RocketMQListenerInitialization
    *@Param [registry]
    *@Author mingj
    *@Date 2020/1/31 22:27
    *@Return void
    **/
    private void registerRocketMQListenerInitialization(BeanDefinitionRegistry registry) {
        String consumerGroup = EnvironmentManager.getProperty(env, "rocketmq.consumerGroup", EnvironmentManager.getAppid()+"ConsumerGroup");
        String nameServerAddress = EnvironmentManager.getProperty(env, "rocketmq.nameServerAddress");
        Integer minThread = MIN_THREAD_NUM;
        Integer maxThread = MAX_THREAD_NUM;
        String minThreadProperty = EnvironmentManager.getProperty(env, "rocketmq.minThread");
        String maxThreadProperty = EnvironmentManager.getProperty(env, "rocketmq.maxThread");
        if (StringUtils.isNotEmpty(minThreadProperty)) {
            minThread = Integer.valueOf(minThreadProperty);
        }
        if (StringUtils.isNotEmpty(maxThreadProperty)) {
            maxThread = Integer.valueOf(maxThreadProperty);
        }

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(RocketMQListenerInitialization.class);
        builder.addPropertyValue("nameServerAddress", nameServerAddress);
        builder.addPropertyValue("consumerGroup", consumerGroup);
        builder.addPropertyValue("maxThread", maxThread);
        builder.addPropertyValue("minThread", minThread);
        registry.registerBeanDefinition("rocketMQListenerInitialization", builder.getRawBeanDefinition());
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.env = (ConfigurableEnvironment) environment;
    }
}
