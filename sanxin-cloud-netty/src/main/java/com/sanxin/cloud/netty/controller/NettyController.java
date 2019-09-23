package com.sanxin.cloud.netty.controller;

import com.sanxin.cloud.netty.enums.CommandEnums;
import com.sanxin.cloud.netty.properties.CommandUtils;
import com.sanxin.cloud.netty.properties.NettySocketHolder;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author arno
 * @version 1.0
 * @date 2019-09-17
 */
@RestController
@RequestMapping("/netty")
public class NettyController {

    @GetMapping(value="/sendCommand")
    public void sendCommand(String boxId,String commond){
        ChannelHandlerContext ctx= NettySocketHolder.get(boxId);
        ctx.channel().writeAndFlush(CommandUtils.sendCommand(commond)).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
//        //登陆成功后借一个充电宝
//        ctx.channel().writeAndFlush(CommandUtils.sendCommand(CommandEnums.x65.getCommand())).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
//        //查看充电宝库存
//        ctx.channel().writeAndFlush(CommandUtils.sendCommand(CommandEnums.x64.getCommand())).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
    }


    @GetMapping(value="/borrowBattery")
    public void borrowBattery(String boxId ,String solt){
        ChannelHandlerContext ctx= NettySocketHolder.get(boxId);
        ctx.channel().writeAndFlush(CommandUtils.sendCommand(CommandEnums.x65.getCommand(),solt)).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
    }
}
