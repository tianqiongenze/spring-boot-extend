package com.example.common.config;

import com.example.common.exception.BaseException;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.*;

/**
 * @version 1.0
 * @ClassName PluginConfigManager
 * @Description TODO描述
 * @Author mingj
 * @Date 2019/10/27 14:49
 **/
public class PluginConfigManager {

    private static final String APP_CAT_PROPERTIES_CLASSPATH = "classpath*:META-INF/example/plugin/**";
    private static final Map<String, Properties> CANT_PROPERTIES_MAP = new HashMap<>();

    static {
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        try {
            //获取资源
            Resource[] resources = patternResolver.getResources(APP_CAT_PROPERTIES_CLASSPATH);
            for (Resource resource : resources) {
                Properties props = new Properties();
                //加载资源
                props.load(resource.getInputStream());
                String fileName = resource.getFilename();
                if (CANT_PROPERTIES_MAP.containsKey(fileName)) {
                    Properties properties = CANT_PROPERTIES_MAP.get(fileName);
                    for (Map.Entry<Object, Object> entry : props.entrySet()) {
                        properties.setProperty((String) entry.getKey(), (String) entry.getValue());
                    }
                } else {
                    //装载资源
                    CANT_PROPERTIES_MAP.put(fileName, props);
                }
            }
        } catch (IOException e) {
            throw new BaseException(e, "SYS002","初始化资源文件失败！", false);
        }
    }

    /**
    *@Description 获取资源配置文件
    *@Param [fileName]
    *@Author mingj
    *@Date 2019/10/27 14:52
    *@Return java.util.Set<java.lang.String>
    **/
    public static Set<String> getPropertyValueSet(String fileName) {
        Set<String> setStr = new HashSet<>();
        Properties property = getProperty(fileName);
        if (property != null) {
            Set<String> strings = property.stringPropertyNames();
            for (String key : strings) {
                setStr.add(property.get(key).toString());
            }
        }
        return setStr;
    }

    /**
    *@Description 获取属性配置
    *@Param [filName]
    *@Author mingj
    *@Date 2019/10/27 14:52
    *@Return java.util.Properties
    **/
    private static Properties getProperty(String filName) {
        return CANT_PROPERTIES_MAP.get(filName);
    }
}
