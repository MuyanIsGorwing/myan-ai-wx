package com.myan.myanai.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
public class ApiConfig {

    @Value("${ds.key}")
    private String key;
    @Value("${ds.url}")
    private String url;

}
