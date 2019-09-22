package com.example.mybatis.config;

import com.example.common.config.ConfigurationImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @version 1.0
 * @ClassName EnableMyBatisImportSelector
 * @Description 开启功能模块选择器
 * @Author mingj
 * @Date 2019/9/22 22:55
 **/
public class EnableMyBatisImportSelector extends ConfigurationImportSelector {

    @Override
    public String[] importSelect(AnnotationMetadata importingClassMetadata) {
        return new String[]{MyBatisAutoConfiguration.class.getName()};
    }
}
