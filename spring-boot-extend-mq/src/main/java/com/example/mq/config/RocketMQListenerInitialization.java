package com.example.mq.config;

import com.example.common.exception.BaseExceotionEnum;
import com.example.common.exception.BaseException;
import com.example.mq.annotation.MQParam;
import com.example.mq.annotation.RocketMQListener;
import com.example.mq.model.RocketMQInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @ClassName RocketMQListenerInitialization
 * @Description MQ消息监听初始化
 * @Author mingj
 * @Date 2020/1/31 22:21
 **/
@Slf4j
public class RocketMQListenerInitialization implements BeanPostProcessor, ApplicationListener<ApplicationReadyEvent> {

    private String nameServerAddress;
    private String consumerGroup;
    private Integer maxThread;
    private Integer minThread;
//    private List<RocketMQInfo> mqInfos = new ArrayList<>();
    private Map<String, RocketMQConfiguration> config = new HashMap<>();

    /**
    *@Description 获取所有订阅的Topic并打印
    *@Param [event]
    *@Author mingj
    *@Date 2020/1/31 22:27
    *@Return void
    **/
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (config.size() > 0) {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
            consumer.setNamesrvAddr(nameServerAddress);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            consumer.setConsumeThreadMax(maxThread);
            consumer.setConsumeThreadMin(minThread);
            for (Map.Entry<String, RocketMQConfiguration> entry:config.entrySet()){

            }
        }
    }

    /**
    *@Description 在Bean初始化之前，扫描被@RocketMQListener扫描的方法，获取方法的属性；
    *@Param [bean, beanName]
    *@Author mingj
    *@Date 2020/1/31 22:24
    *@Return java.lang.Object
    **/
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(RocketMQListener.class)){
                RocketMQListener listener = method.getAnnotation(RocketMQListener.class);
                String topic = listener.topic();
                String consumerGroup = listener.consumerGroup();
                String tags = listener.tags();
                String consumeThreadMin = listener.consumeThreadMin();
                String consumeThreadMax = listener.consumeThreadMax();
                if (tags.equals("*")){
                    config.put(topic + "_" + tags, getConfig(bean, method, topic, tags));
                } else {
                    String[] tagArray = tags.split("\\|\\|");
                    for (String tag : tagArray) {
                        config.put(topic + "_" + tag, getConfig(bean, method, topic, tag));
                    }
                }
            }
        }
        return bean;
    }

    /**
    *@Description 读取MQ相关配置信息
    *@Param [bean, method, topic, tags]
    *@Author mingj
    *@Date 2020/2/14 16:28
    *@Return com.example.mq.config.RocketMQCnofiguration
    **/
    private RocketMQConfiguration getConfig(Object bean, Method method, String topic, String tags) {
        RocketMQConfiguration configuration = new RocketMQConfiguration();
        configuration.setTopic(topic);
        configuration.setMethod(method);
        configuration.setObj(bean);
        configuration.setTag(tags);
        Parameter[] parameters = method.getParameters();
        if (parameters.length <= 0) {
            throw new BaseException(BaseExceotionEnum.ROCKET_MQ_INIT_ERROR.getCode(), BaseExceotionEnum.ROCKET_MQ_INIT_ERROR.getMessage(), BaseExceotionEnum.ROCKET_MQ_INIT_ERROR.getStatus());
        }
        List<Map<String, Object>> params = new ArrayList<>();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            MQParam param = parameter.getAnnotation(MQParam.class);
            Map<String, Object> map = new HashMap<>();
            if (param == null) {
                map.put("name", parameter.getName());
                map.put("serialize", "String");
            } else {
                map.put("name", param.name());
                map.put("serialize", param.serialize());
            }
            params.add(map);
        }
        configuration.setParams(params);
        return configuration;
    }
}
