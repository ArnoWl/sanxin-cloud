package com.sanxin.cloud.netty.handle;

import com.sanxin.cloud.netty.enums.CommandEnums;
import com.sanxin.cloud.netty.properties.AppCommandUtils;
import com.sanxin.cloud.netty.properties.AppNettySocketHolder;
import com.sanxin.cloud.netty.properties.CommandUtils;
import com.sanxin.cloud.netty.properties.NettySocketHolder;
import com.sanxin.cloud.netty.server.AppNettyServer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 数据接受Handler
 * @Author arno
 */
@Component
@ChannelHandler.Sharable
public class AppDataAcceptHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static Logger log = LoggerFactory.getLogger(AppDataAcceptHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        ctx.fireChannelActive();
        if (log.isDebugEnabled()) {
            log.debug(ctx.channel().remoteAddress() + "");
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //获取客户端传输过来的信息
        String content = msg.text();
        System.out.println("接收到的数据：" + content);
        String out_remark = AppCommandUtils.command(ctx, content);
        if(!StringUtils.isEmpty(out_remark)) {
            ctx.channel().writeAndFlush(new TextWebSocketFrame(out_remark)).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        //掉线删除渠道
        AppNettySocketHolder.remove(ctx);
        log.info("APP客户端："+ctx.channel().remoteAddress()+"断开连接");
        ctx.channel().close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        log.error(cause.getMessage(), cause);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt){
        log.info("进入空闲处理");
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt ;
            log.info("state"+event.state());
            if (event.state() == IdleState.READER_IDLE) {
                log.info("===APP客户端["+ctx.channel().remoteAddress()+"]===(READER_IDLE 读超时)");
                channelInactive(ctx);
            } else if (event.state() == IdleState.WRITER_IDLE) {
                log.info("===APP客户端["+ctx.channel().remoteAddress()+"]===(WRITER_IDLE 写超时)！");
            }else if (event.state() == IdleState.ALL_IDLE) {
                log.info("===APP客户端["+ctx.channel().remoteAddress()+"]===(ALL_IDLE 总超时 重新连接)");
            }
        }
    }
}
