package com.example.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.util.StringUtils;

/**
 * @version 1.0
 * @ClassName ApplicationPreparedEventListener
 * @Description TODO描述
 * @Author mingj
 * @Date 2019/12/30 17:38
 **/
public class ApplicationPreparedEventListener implements ApplicationListener<ApplicationPreparedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationPreparedEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent applicationPreparedEvent) {
        String env = System.getProperty("env");
        if (StringUtils.isEmpty(env)){
            System.setProperty("env", "dev");
            logger.info("No system environment set, falling back to default environment: dev");
        }
        logger.info("ApplicationPreparedEvent...");
    }
}