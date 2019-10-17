package com.sanxin.cloud.app.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 订单服务
 * @author shengwu ni
 * "@EnableEurekaClient" 服务启动后会自动注册到eureka服务中心
 */
@SpringBootApplication
@EnableEurekaClient
//启用feign进行远程调用
@EnableFeignClients
@EnableScheduling
@ComponentScan(basePackages = "com.sanxin.cloud")
public class AppApiServer {

    public static void main(String[] args) {
        SpringApplication.run(AppApiServer.class, args);
    }
}
