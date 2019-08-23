package com.sanxin.cloud.admin.web;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

/**
 * 订单服务
 * @author shengwu ni
 * "@EnableEurekaClient" 服务启动后会自动注册到eureka服务中心
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = "com.sanxin.cloud")
public class AdminWeb {

    public static void main(String[] args) {
        SpringApplication.run(AdminWeb.class, args);
    }
}
