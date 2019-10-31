package com.example.mongodb.config;

import com.example.common.config.ConfigurationImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @version 1.0
 * @ClassName EnableMongoDbImportSelector
 * @Description TODO描述
 * @Author mingj
 * @Date 2019/10/31 14:31
 **/
public class EnableMongoDbImportSelector extends ConfigurationImportSelector {

    @Override
    public String[] importSelect(AnnotationMetadata importingClassMetadata) {
        return new String[0];
    }
}
