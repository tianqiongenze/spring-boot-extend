package com.example.mybatis.plugin;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @version 1.0
 * @ClassName ExamplePlugin
 * @Description 拦截器模板
 * @Author mingj
 * @Date 2019/11/14 21:31
 **/
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class})
})
public class ExamplePlugin implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(ExamplePlugin.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        logger.info("【示范拦截器】启动");
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
