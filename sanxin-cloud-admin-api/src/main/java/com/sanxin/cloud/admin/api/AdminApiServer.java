package com.sanxin.cloud.admin.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * 订单服务
 * @author shengwu ni
 * "@EnableEurekaClient" 服务启动后会自动注册到eureka服务中心
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = "com.sanxin.cloud")
public class AdminApiServer {

    public static void main(String[] args) {
        SpringApplication.run(AdminApiServer.class, args);
    }
}
