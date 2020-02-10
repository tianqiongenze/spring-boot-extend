package com.example.mq.plugin;

import org.apache.rocketmq.client.producer.DefaultMQProducer;

/**
 * @version 1.0
 * @ClassName RocketMQTemplate
 * @Description MQ消息发送模板
 * @Author mingj
 * @Date 2020/1/31 22:15
 **/
public class RocketMQTemplate {

    private DefaultMQProducer producer;

    public void setProducer(DefaultMQProducer producer) {
        this.producer = producer;
    }
}
