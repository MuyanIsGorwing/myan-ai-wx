package com.myan.myanai.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceConfigLoader implements BeanPostProcessor, EnvironmentAware {

    private ConfigurableEnvironment environment;

    @Value("${mysqlDataSource.path}")
    private String path;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }

    @SneakyThrows
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        if (bean instanceof MybatisAutoConfiguration){
            //1、读取db.json文件获取数据库信息
            String fileUrl = FileUtils.readFileToString(new File(path + "/db.json"), Charset.defaultCharset());
            JSONObject db = JSON.parseObject(fileUrl);
            //2、将数据库信息放进environment中
            environment.getSystemProperties().put("spring.datasource.type", "com.zaxxer.hikari.HikariDataSource");
            environment.getSystemProperties().put("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver");
            environment.getSystemProperties().put("spring.datasource.url", db.getString("url"));
            environment.getSystemProperties().put("spring.datasource.password", db.getString("password"));
            environment.getSystemProperties().put("spring.datasource.username", db.getString("username"));
        }

        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

}
