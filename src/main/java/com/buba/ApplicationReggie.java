package com.buba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling//开启定时器
@EnableAsync//开启异步
@ServletComponentScan//开启过滤器注解
@SpringBootApplication
public class ApplicationReggie {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationReggie.class,args);
    }
}
