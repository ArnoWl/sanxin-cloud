package com.sanxin.cloud.images;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 图片服务
 * @author xiaoky
 * "@EnableEurekaClient" 服务启动后会自动注册到eureka服务中心
 */
@SpringBootApplication
@EnableEurekaClient
public class ImagesServer {

    public static void main(String[] args) {
        SpringApplication.run(ImagesServer.class, args);
    }
}
