package com.sanxin.cloud.netty.controller;

import com.sanxin.cloud.netty.enums.CommandEnums;
import com.sanxin.cloud.netty.properties.CommandUtils;
import com.sanxin.cloud.netty.properties.NettySocketHolder;
import com.sanxin.cloud.netty.service.HandleService;
import com.sanxin.cloud.service.BDeviceTerminalService;
import com.sanxin.cloud.service.system.login.LoginTokenService;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private HandleService handleService;
    @Autowired
    private LoginTokenService loginTokenService;

    @GetMapping(value="/sendCommand")
    public void sendCommand(String boxId,String commond){
        ChannelHandlerContext ctx= NettySocketHolder.get(boxId);
        ctx.channel().writeAndFlush(CommandUtils.sendCommand(commond)).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
//        ctx.channel().writeAndFlush(CommandUtils.sendCommand(CommandEnums.x65.getCommand())).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
//        //查看充电宝库存
//        ctx.channel().writeAndFlush(CommandUtils.sendCommand(CommandEnums.x64.getCommand())).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
    }


    @GetMapping(value="/borrowBattery")
    public void borrowBattery(String boxId, String slot){
        ChannelHandlerContext ctx= NettySocketHolder.get(boxId);
        // 发送借充电宝前先发送一次查库存指令
        // ctx.channel().writeAndFlush(CommandUtils.sendCommand(CommandEnums.x64.getCommand())).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        // String slot = handleService.getMostCharge(boxId);
        String command=CommandUtils.sendCommand(CommandEnums.x64.getCommand());
        ctx.channel().writeAndFlush(command).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
    }

    @GetMapping(value = "/popBattery")
    public void popBattery(String boxId, String slot) {
        // 强制弹出充电宝
        ChannelHandlerContext ctx= NettySocketHolder.get(boxId);
        // 发送借充电宝前先发送一次查库存指令
        ctx.channel().writeAndFlush(CommandUtils.sendCommand(CommandEnums.x80.getCommand(), slot)).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
    }

}
