package com.zxtechai.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//微服务分布式设计使用RestTemplate请求http接口返回数据不使用SQL查询
//配置注解@Configuration才能使用@Bean注入到IOC容器
@Configuration
@Slf4j
public class RestTemplateConfiguration {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
