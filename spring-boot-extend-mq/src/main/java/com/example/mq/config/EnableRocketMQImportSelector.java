package com.example.mq.config;

import com.example.common.config.ConfigurationImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @version 1.0
 * @ClassName EnableRocketMQImportSelector
 * @Description MQ选择器
 * @Author mingj
 * @Date 2020/1/31 22:11
 **/
public class EnableRocketMQImportSelector extends ConfigurationImportSelector {

    @Override
    public String[] importSelect(AnnotationMetadata importingClassMetadata) {
        return new String[]{RocketMQAutoConfiguration.class.getName()};
    }
}
