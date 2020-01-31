package com.example.mq.annotation;

import com.example.mq.config.EnableRocketMQImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @version 1.0
 * @ClassName EnableRocketMQ
 * @Description TODO描述
 * @Author mingj
 * @Date 2020/1/31 22:10
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({EnableRocketMQImportSelector.class})
public @interface EnableRocketMQ {


}
