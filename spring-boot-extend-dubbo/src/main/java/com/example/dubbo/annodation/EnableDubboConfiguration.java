package com.example.dubbo.annodation;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.example.dubbo.config.EnableDubboImportSelector;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @version 1.0
 * @ClassName EnableDubboConfiguration
 * @Description dubbo服务开启注解
 * @Author mingj
 * @Date 2019/12/16 22:56
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@DubboComponentScan
@Import({EnableDubboImportSelector.class})
public @interface EnableDubboConfiguration {

    String port() default "20880";

    String protocol() default "dubbo";

    @AliasFor(annotation = DubboComponentScan.class, attribute = "basePackages")
    String[] scanPackageName() default {"com.example"};
}
