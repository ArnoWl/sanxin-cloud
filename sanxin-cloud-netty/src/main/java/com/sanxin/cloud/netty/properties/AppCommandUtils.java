package com.sanxin.cloud.netty.properties;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.redis.RedisUtils;
import com.sanxin.cloud.config.redis.SpringBeanFactoryUtils;
import com.sanxin.cloud.dto.BTerminalVo;
import com.sanxin.cloud.entity.BDeviceTerminal;
import com.sanxin.cloud.netty.enums.AppCommandEnums;
import com.sanxin.cloud.netty.enums.CommandEnums;
import com.sanxin.cloud.netty.service.HandleService;
import com.sanxin.cloud.service.system.login.LoginTokenService;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

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

    /**
     * 接收到机器的指令
     * @param content  机器发送过来的16进制
     * @return
     */
    public static String command(ChannelHandlerContext ctx, String content) {
        String out_str = "";//输入字符串 不需要16进制
        if (!StringUtils.isEmpty(content)) {
            JSONObject json = JSONObject.parseObject(content);
            String command = json.getString("command");
            AppCommandEnums enums = AppCommandEnums.getCommandFun(command);
            if (enums == null) {
                return JSON.toJSONString(RestResult.fail("fail"));
            }
            String token = json.getString("token");
            switch (enums) {
                case x10000:
                    // app用户端登录连接和响应
                    try {
                        Integer cid = loginTokenService.validLoginCid(token);
                        //登陆记录
                        AppNettySocketHolder.put(cid.toString(), ctx);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return JSON.toJSONString(RestResult.fail("1001","Token is invalid, please log in again",null,null));
                    }
                    out_str = JSON.toJSONString(RestResult.success("success"));
                    break;
                case x10001:
                    // app用户端登录连接和响应
                    out_str = content;
                    break;
                case x10002:
                    // 借充电宝信息及响应
                    Integer cid = null;
                    // 机柜编号
                    String boxId = json.getString("boxId");
                    try {
                        cid = loginTokenService.validLoginCid(token);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return JSON.toJSONString(RestResult.fail("1001","Token is invalid, please log in again",null,null));
                    }
                    // 获取电量最多的充电宝
                    BTerminalVo mostCharge = RedisUtils.getInstance().getMostCharge(boxId);
                    if (mostCharge == null) {
                        return JSON.toJSONString(RestResult.fail("fail"));
                    }
                    // 修改充电宝当前使用用户
                    BDeviceTerminal terminal = new BDeviceTerminal();
                    BeanUtils.copyProperties(mostCharge, terminal);
                    terminal.setUseCid(cid);
                    boolean result = handleService.handleUpdateTerminal(terminal);
                    if (!result) {
                        return JSON.toJSONString(RestResult.fail("借用失败"));
                    }
                    log.info("APP_发送借充电宝指令");
                    // 发送借充电宝指令
                    ChannelHandlerContext otherCtx = NettySocketHolder.get(boxId);
                    otherCtx.channel().writeAndFlush(CommandUtils.sendCommand(CommandEnums.x65.getCommand(), mostCharge.getSlot())).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                    log.info("APP_发送借充电宝指令完成");
                    break;
                default:
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
                    out_str = JSON.toJSONString(RestResult.success("借用失败"));
                } else {
                    out_str = JSON.toJSONString(RestResult.success("success"));
                }
                break;
            case x10003:
                // app还充电宝响应
                // flag null支付成功  1 免密支付余额不足  2 非免密支付
                status = params[0];
                if ("0".equals(status)) {
                    return JSON.toJSONString(RestResult.success("归还失败"));
                }
                String cid = params[1];
                String flag = params[2];
                String terminalId = params[3];
                if (StaticUtils.RETURN_NOT_FREE_SECRET.equals(flag)) {
                    out_str = JSON.toJSONString(RestResult.success("归还成功", null, flag));
                } else {
                    RestResult result = handleService.queryReturnMsg(cid, terminalId);
                    out_str = JSON.toJSONString(result);
                }
                break;
        }
        log.info("发送内容----------【" + out_str + "】----------");
        return out_str;
    }

}
