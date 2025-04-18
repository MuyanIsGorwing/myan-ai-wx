package com.myan.myanai;

import com.myan.myanai.config.ApiConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.myan.myanai")
@MapperScan(basePackages = {"com.myan.myanai.dao"})
public class MyanAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyanAiApplication.class, args);
	}

}
