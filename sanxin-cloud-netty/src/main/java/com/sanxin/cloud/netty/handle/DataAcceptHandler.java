package com.sanxin.cloud.netty.handle;

import com.sanxin.cloud.netty.properties.CommandUtils;
import com.sanxin.cloud.netty.properties.NettySocketHolder;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 数据接受Handler
 * @Author arno
 */
@Component
@ChannelHandler.Sharable
public class DataAcceptHandler extends SimpleChannelInboundHandler<String> {

    private static Logger log = LoggerFactory.getLogger(DataAcceptHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        ctx.fireChannelActive();
        if (log.isDebugEnabled()) {
            log.debug(ctx.channel().remoteAddress() + "");
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        //掉线删除渠道
        NettySocketHolder.remove(ctx);
        log.info("客户端："+ctx.channel().remoteAddress()+"断开连接");
        ctx.channel().close();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String content)throws  Exception{
        String out_remark=CommandUtils.command(ctx,content);
        if(!StringUtils.isEmpty(out_remark)){
            ctx.channel().writeAndFlush(out_remark).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        log.error(cause.getMessage(), cause);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt){
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt ;
            if (event.state() == IdleState.READER_IDLE) {
                log.info("===客户端["+ctx.channel().remoteAddress()+"]===(READER_IDLE 读超时)");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                log.info("===客户端["+ctx.channel().remoteAddress()+"]===(WRITER_IDLE 写超时)！");
            }else if (event.state() == IdleState.ALL_IDLE) {
                log.info("===客户端["+ctx.channel().remoteAddress()+"]===(ALL_IDLE 总超时 重新连接)");
            }
        }
    }


    public static void main(String[] args) {
//        String token= RandNumUtils.get(RandNumType.NUMBER,4);
//        System.out.println(token);
//        String hex_token= HexUtils.str2HexStr(token);
//        System.out.println(hex_token.replace(" ",""));
//        String out_str="0008"+CommandEnums.x60.getCommand()+"0100"+hex_token.replace(" ","")+"01";
//        System.out.println(out_str);
//        out_str=HexUtils.hexStr2Str(out_str);
//        System.out.println(out_str);
//        System.out.println(HexUtils.str2HexStr(out_str));
        String a="0011";
        System.out.println(Integer.parseInt(a,16));
    }
}
