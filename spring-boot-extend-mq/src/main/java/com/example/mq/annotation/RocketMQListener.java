package com.example.mq.annotation;

import java.lang.annotation.*;

/**
 * @version 1.0
 * @ClassName RocketMQListener
 * @Description MQ消息监听
 * @Author mingj
 * @Date 2020/1/31 22:31
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RocketMQListener {

    String consumerGroup();

    String tags() default "*";

    String consumeThreadMin() default "";

    String consumeThreadMax() default "";

    @Target({ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Param {
        String name();

        String serialize() default "String";
    }
}
