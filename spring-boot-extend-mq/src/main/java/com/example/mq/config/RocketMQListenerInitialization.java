package com.example.mq.config;

import com.example.mq.annotation.RocketMQListener;
import com.example.mq.model.RocketMQInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
    private List<RocketMQInfo> mqInfos = new ArrayList<>();

    /**
    *@Description 获取所有订阅的Topic并打印
    *@Param [event]
    *@Author mingj
    *@Date 2020/1/31 22:27
    *@Return void
    **/
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        for (RocketMQInfo info : mqInfos) {
            log.info("RocketMQ订阅消费信息，消费组：{}，tags：{}，消费最大线程数：{}，消费最小线程数：{}", info.getConsumerGroup(), info.getTags(), info.getConsumeThreadMax(), info.getConsumeThreadMin());
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
                String consumerGroup = listener.consumerGroup();
                String tags = listener.tags();
                String consumeThreadMin = listener.consumeThreadMin();
                String consumeThreadMax = listener.consumeThreadMax();
                RocketMQInfo info = new RocketMQInfo(listener.consumerGroup(), listener.tags(), listener.consumeThreadMin(), listener.consumeThreadMax());
                mqInfos.add(info);
            }

        }
        return null;
    }
}
