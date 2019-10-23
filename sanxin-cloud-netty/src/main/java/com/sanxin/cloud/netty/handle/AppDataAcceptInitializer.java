package com.sanxin.cloud.netty.handle;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 初始化ChannelPipeline
 * @Author rex
 * 2018/7/25
 */
@Component
public class AppDataAcceptInitializer extends ChannelInitializer<SocketChannel> {

    private static Logger log = LoggerFactory.getLogger(AppDataAcceptInitializer.class);
    @Autowired
    private AppDataAcceptHandler appDataAcceptHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        log.info("==================netty报告==================");
        log.info("APP服务端===" + socketChannel.localAddress().getHostName()+":" + socketChannel.localAddress().getPort());
        log.info("APP客户端==="+socketChannel.remoteAddress().getHostName()+":"+socketChannel.remoteAddress().getPort());
        log.info("==================netty报告完毕==================");
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 添加超时 APP表示5S发送心跳我们设置7S表示没有连接上就重新来
        pipeline.addLast(new IdleStateHandler(0, 0, 7, TimeUnit.SECONDS));

        // webSocket基于http协议，所以要有http编译器
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler("/websocket"));
        pipeline.addLast(appDataAcceptHandler);
    }
}
