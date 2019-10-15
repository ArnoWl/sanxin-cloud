package com.sanxin.cloud.netty.handle;

import com.sanxin.cloud.netty.encode.AppNettyDecode;
import com.sanxin.cloud.netty.encode.NettyDecode;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;
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
        // 添加超时 机柜表示30S发送心跳我们设置32S表示没有连接上就重新来
        pipeline.addLast(new IdleStateHandler(0, 0, 32, TimeUnit.SECONDS));
//        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
//        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new AppNettyDecode());
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(appDataAcceptHandler);
    }
}
