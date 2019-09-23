package com.sanxin.cloud.netty.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author arno
 * @version 1.0
 * @date 2019-09-02
 */
@ConfigurationProperties(prefix = "netty")
@Component
public class NettyConfig {

    private int tcpPort;

    public int getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(int tcpPort) {
        this.tcpPort = tcpPort;
    }
}
