package com.example.dubbo.config;

import com.example.common.config.ConfigurationImportSelector;
import com.example.common.constant.EnvironmentManager;
import com.example.dubbo.annodation.EnableDubboConfiguration;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

/**
 * @version 1.0
 * @ClassName EnableDubboConfigurationImportSelector
 * @Description dubbo服务配置
 * @Author mingj
 * @Date 2019/12/16 23:00
 **/
public class EnableDubboImportSelector extends ConfigurationImportSelector {

    @Override
    public String[] importSelect(AnnotationMetadata importingClassMetadata) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EnableDubboConfiguration.class.getName()));
        String port = attributes.getString("port");
        String protocol = attributes.getString("protocol");
        String[] scanPackageName = attributes.getStringArray("scanPackageName");
        if(!StringUtils.isEmpty(port)) {
            EnvironmentManager.setProperty(EnvironmentManager.DUBBO_PORT, port);
        }
        if(!StringUtils.isEmpty(protocol)) {
            EnvironmentManager.setProperty(EnvironmentManager.DUBBO_PROTOCOL, protocol);
        }
        if(scanPackageName.length > 0) {
            String packageName = String.join(",", scanPackageName);
            EnvironmentManager.setProperty(EnvironmentManager.DUBBO_SCAN_PACKAGE_NAME, packageName);
        }
        return new String[]{DubboAutoConfiguration.class.getName()};
    }
}
