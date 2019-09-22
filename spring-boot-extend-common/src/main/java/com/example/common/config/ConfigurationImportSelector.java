package com.example.common.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * @version 1.0
 * @ClassName ConfigurationImportSelector
 * @Description 配置功能选择器
 * @Author mingj
 * @Date 2019/9/22 23:02
 **/
public abstract class ConfigurationImportSelector implements ImportSelector {

    //统一的开启配置的选择器

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        EnableType enableType = EnableType.getByValue(this.getClass().getSimpleName());
        if (enableType != null && isEnable(enableType)) {
            return importSelect(annotationMetadata);
        }
        return new String[]{};
    }

    public abstract String[] importSelect(AnnotationMetadata importingClassMetadata);


    /**
     * 注解类型
     */
    enum EnableType {
        ENABLE_MYBATIS("EnableMyBatisImportSelector", "EnableMybatis");

        private final String value;

        private final String param;


        EnableType(String value, String param) {
            this.value = value;
            this.param = param;
        }

        public String getValue() {
            return value;
        }

        public String getParam() {
            return param;
        }

        public static EnableType getByValue(String value) {
            return Arrays.stream(values()).filter(_enum -> _enum.getValue().equals(value)).findFirst().orElseGet(() -> null);
        }
    }
    
    /**
    *@Description 判断是否被选中
    *@Param [enableType]
    *@Author mingj
    *@Date 2019/9/22 23:05
    *@Return boolean
    **/
    private boolean isEnable(EnableType enableType) {
        try {
            return StringUtils.isEmpty(System.getProperty(enableType.getParam())) ? true : Boolean.valueOf(System.getProperty(enableType.getParam()));
        } catch (Exception e) {
            return true;
        }

    }
}
