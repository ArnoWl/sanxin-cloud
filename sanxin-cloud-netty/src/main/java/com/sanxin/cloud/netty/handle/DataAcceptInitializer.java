package com.sanxin.cloud.netty.handle;

import com.sanxin.cloud.netty.encode.NettyDecode;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
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
public class DataAcceptInitializer extends ChannelInitializer<SocketChannel> {

    private static Logger log = LoggerFactory.getLogger(DataAcceptInitializer.class);
    @Autowired
    private DataAcceptHandler dataAcceptHandler;


    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        log.info("==================netty报告==================");
        log.info("服务端===" + socketChannel.localAddress().getHostName()+":" + socketChannel.localAddress().getPort());
        log.info("客户端==="+socketChannel.remoteAddress().getHostName()+":"+socketChannel.remoteAddress().getPort());
        log.info("==================netty报告完毕==================");
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 添加超时 机柜表示30S发送心跳我们设置32S表示没有连接上就重新来
        // pipeline.addLast(new IdleStateHandler(0, 0, 3, TimeUnit.SECONDS));
        pipeline.addLast(new IdleStateHandler(0, 0, 0, TimeUnit.SECONDS));
//        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
//        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new NettyDecode());
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(dataAcceptHandler);
    }
}
