package com.myan.myanai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "ds-api")
public class ApiConfig {

    private String key;

    private String url;



}
