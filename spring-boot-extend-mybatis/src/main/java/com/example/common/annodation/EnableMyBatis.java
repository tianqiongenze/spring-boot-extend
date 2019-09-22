package com.example.common.annodation;

import com.example.common.config.EnableMyBatisImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @version 1.0
 * @ClassName EnableMyBatis
 * @Description 注解类
 * @Author mingj
 * @Date 2019/9/22 22:55
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({EnableMyBatisImportSelector.class})
public @interface EnableMyBatis {

}
