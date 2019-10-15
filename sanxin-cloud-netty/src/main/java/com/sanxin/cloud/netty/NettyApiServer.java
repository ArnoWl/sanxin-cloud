package com.sanxin.cloud.netty;

import com.sanxin.cloud.netty.server.AppNettyServer;
import com.sanxin.cloud.netty.server.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 订单服务
 * @author shengwu ni
 * "@EnableEurekaClient" 服务启动后会自动注册到eureka服务中心
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = "com.sanxin.cloud")
public class NettyApiServer {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(NettyApiServer.class, args);
        NettyServer nettyServer=context.getBean(NettyServer.class);
        nettyServer.init();
        AppNettyServer appNettyServer=context.getBean(AppNettyServer.class);
        appNettyServer.init();
    }

}
