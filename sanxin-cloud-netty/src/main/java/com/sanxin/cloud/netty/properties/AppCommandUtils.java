package com.sanxin.cloud.netty.properties;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.redis.RedisUtils;
import com.sanxin.cloud.config.redis.SpringBeanFactoryUtils;
import com.sanxin.cloud.dto.BTerminalVo;
import com.sanxin.cloud.entity.BDeviceTerminal;
import com.sanxin.cloud.entity.OrderMain;
import com.sanxin.cloud.enums.OrderStatusEnums;
import com.sanxin.cloud.exception.LoginOutException;
import com.sanxin.cloud.netty.config.CommandResult;
import com.sanxin.cloud.netty.enums.AppCommandEnums;
import com.sanxin.cloud.netty.enums.CommandEnums;
import com.sanxin.cloud.netty.service.HandleService;
import com.sanxin.cloud.service.OrderMainService;
import com.sanxin.cloud.service.system.login.LoginTokenService;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Random;

/**
 * app指令
 * @author arno
 * @version 1.0
 * @date 2019-09-17
 */
public class AppCommandUtils {

    private static Logger log = LoggerFactory.getLogger(AppCommandUtils.class);
    private static LoginTokenService loginTokenService = SpringBeanFactoryUtils.getApplicationContext().getBean(LoginTokenService.class);
    private static HandleService handleService = SpringBeanFactoryUtils.getApplicationContext().getBean(HandleService.class);
    private static OrderMainService orderMainService = SpringBeanFactoryUtils.getApplicationContext().getBean(OrderMainService.class);

    /**
     * 接收到机器的指令
     * @param content  机器发送过来的16进制
     * @return
     */
    public static String command(ChannelHandlerContext ctx, String content) throws InterruptedException {
        log.info("App发送的内容"+content);
        //输入字符串 不需要16进制
        String out_str = "";
        if (!StringUtils.isEmpty(content)) {
            JSONObject json = null;
            String command = null;
            AppCommandEnums enums = null;
            String token = null;
            try {
                json = JSONObject.parseObject(content);
                command = json.getString("command");
                enums = AppCommandEnums.getCommandFun(command);
                if (enums == null) {
                    return CommandResult.fail("fail");
                }
                token = json.getString("token");
            } catch (Exception e) {
                return content;
            }
            switch (enums) {
                case x10000:
                    // app用户端登录连接和响应
                    Integer cid = null;
                    try {
                        cid = loginTokenService.validLoginCid(token);
                        //登陆记录
                        AppNettySocketHolder.put(cid.toString(), ctx);
                    } catch (Exception ex) {
                        return CommandResult.fail("1001","Token is invalid, please log in again", null, command);
                    }
                    QueryWrapper<OrderMain> wrapper = new QueryWrapper<>();
                    wrapper.eq("cid", cid).eq("del", StaticUtils.STATUS_NO)
                            .eq("order_status", OrderStatusEnums.USING.getId());
                    List<OrderMain> list = orderMainService.list(wrapper);
                    if (list != null && list.size() > 0) {
                        return CommandResult.success("1000", "success", null, command);
                    } else {
                        wrapper = new QueryWrapper<>();
                        wrapper.eq("cid", cid).eq("del", StaticUtils.STATUS_NO)
                                .eq("order_status", OrderStatusEnums.CONFIRMED.getId());
                        list = orderMainService.list(wrapper);
                        if (list != null && list.size() > 0) {
                            return CommandResult.success("1000", "success", null, command);
                        }
                    }
                    out_str = CommandResult.success("success", null, command);
                    break;
                case x10001:
                    // app用户端登录连接和响应
                    out_str = CommandResult.success("success", json, command);
                    break;
                case x10002:
                    // 借充电宝信息及响应
                    // 机柜编号
                    String boxId = json.getString("boxId");
                    try {
                        cid = loginTokenService.validLoginCid(token);
                    } catch (LoginOutException ex) {
                        return CommandResult.fail("1001", "Token is invalid, please log in again", null, command);
                    }
                    wrapper = new QueryWrapper<>();
                    wrapper.eq("cid", cid).in("order_status", OrderStatusEnums.USING.getId(), OrderStatusEnums.CONFIRMED.getId())
                        .eq("del", StaticUtils.STATUS_NO);
                    Integer orderNum = orderMainService.count(wrapper);
                    if (orderNum>0) {
                        return CommandResult.fail("您还有未完成订单", null, command);
                    }

                    BTerminalVo mostCharge = null;
                    Integer index = 0;
                    while (mostCharge == null) {
                        // 获取电量最多的充电宝
                        try {
                            mostCharge = RedisUtils.getInstance().getMostCharge(boxId, index);
                            log.info("Redis里面取到的充电宝信息"+mostCharge);
                        } catch (Exception e) {
                            // 机柜库存没有可借用的充电宝了
                            return CommandResult.fail("fail", null, command);
                        }
                        if (mostCharge == null) {
                            return CommandResult.fail("fail", null, command);
                        }
                        log.info("获取到的充电宝"+mostCharge.getTerminalId()+"槽位"+mostCharge.getSlot());
                        // 查询该充电宝是否被使用(是否有人已创建过订单)
                        List<OrderMain> orderList = handleService.queryUseOrderByTerminal(mostCharge.getTerminalId());
                        System.out.println("订单信息"+orderList.toString());
                        // 充电宝被使用中
                        if (orderList != null && orderList.size()>0) {
                            log.info("充电宝被使用中");
                            mostCharge = null;
                            index ++;
                        }
                    }
                    // TODO 1
                    String perCommand = AppCommandEnums.x10004.getCommand();
                    Integer per = new Random().nextInt(20) + 30;
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(CommandResult.success("success", per, perCommand))).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                    // 生成一个创建中的订单，代表该充电宝已被使用
                    RestResult returnResult = handleService.handleCreateUseOrder(cid, token, boxId, mostCharge.getTerminalId());
                    if (!returnResult.status) {
                        return CommandResult.fail(returnResult.getMsg(), returnResult.getData(), command);
                    }
                    // TODO 2
                    per = new Random().nextInt(20) + 50;
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(CommandResult.success("success", per, perCommand))).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                    Thread.sleep(1000);
                    // 修改充电宝当前使用用户
                    BDeviceTerminal terminal = new BDeviceTerminal();
                    BeanUtils.copyProperties(mostCharge, terminal);
                    terminal.setUseCid(cid);
                    boolean result = handleService.handleUpdateTerminal(terminal);
                    if (!result) {
                        return CommandResult.fail("借用失败", null, command);
                    }
                    // TODO 3
                    per = new Random().nextInt(20) + 80;
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(CommandResult.success("success", per, perCommand))).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                    log.info("APP_发送借充电宝指令");
                    // 发送借充电宝指令
                    ChannelHandlerContext otherCtx = NettySocketHolder.get(boxId);
                    if (otherCtx == null) {
                        out_str = CommandResult.fail("借用失败", null, command);
                    } else {
                        otherCtx.channel().writeAndFlush(CommandUtils.sendCommand(CommandEnums.x65.getCommand(), mostCharge.getSlot())).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                    }
                    // //
                    // Thread.sleep(1000);
                    // per = 100;
                    // ctx.channel().writeAndFlush(new TextWebSocketFrame(CommandResult.success("success", per, AppCommandEnums.x10004.getCommand()))).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                    // Thread.sleep(1000);
                    // out_str = CommandResult.success("借用成功", null, command);
                    log.info("APP_发送借充电宝指令完成");
                    break;
                case x10003:
                    JSONObject reObj = new JSONObject();
                    reObj.put("command", AppCommandEnums.x10002.getCommand());
                    reObj.put("per", new Random().nextInt(10) + 30);
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(CommandResult.success(reObj))).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                    Thread.sleep(1000);
                    reObj.put("per", new Random().nextInt(20) + 50);
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(CommandResult.success(reObj))).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                    Thread.sleep(1000);
                    reObj.put("per", new Random().nextInt(20) + 80);
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(CommandResult.success(reObj))).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                    break;
                default:
                    out_str = content;
                    break;
            }
        }
        log.info("回复内容----------" + out_str + "----------");
        return out_str;
    }

    /**
     * 发送指令
     * @param command
     * @return
     */
    public static String sendCommand(String command, String... params) {
        String out_str = "";
        log.info("APP发送指令----------【" + command + "】----------");
        AppCommandEnums enums = AppCommandEnums.getCommandFun(command);
        log.info("APP发送指令----------【" + command + ":" + enums.getRemark() + "】----------");
        switch (enums) {
            case x10002:
                // 借充电宝信息及响应
                log.info("APP_借充电宝响应");
                String status = params[0];
                if ("0".equals(status)) {
                    out_str = CommandResult.fail("借用失败", null, command);
                } else {
                    out_str = CommandResult.success("success", null, command);
                }
                break;
            case x10003:
                // app还充电宝响应
                // flag null支付成功  1 免密支付余额不足  2 非免密支付
                status = params[0];
                if ("0".equals(status)) {
                    return CommandResult.success("归还失败", null, command);
                }
                String cid = params[1];
                String flag = params[2];
                String terminalId = params[3];
                if (StaticUtils.RETURN_NOT_FREE_SECRET.equals(flag)) {
                    out_str = CommandResult.success("归还成功", flag, command);
                } else {
                    RestResult result = handleService.queryReturnMsg(cid, terminalId);
                    if (result.status) {
                        out_str = CommandResult.success(result.getMsg(), result.getData(), command);
                    } else {
                        out_str = CommandResult.fail(result.getMsg(), result.getData(), command);
                    }
                }
                break;
            default:
                break;
        }
        log.info("发送内容----------【" + out_str + "】----------");
        return out_str;
    }

}
