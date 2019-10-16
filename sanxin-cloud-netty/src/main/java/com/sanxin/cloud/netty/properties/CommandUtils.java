package com.sanxin.cloud.netty.properties;

import com.sanxin.cloud.common.random.RandNumUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.redis.RedisUtils;
import com.sanxin.cloud.config.redis.SpringBeanFactoryUtils;
import com.sanxin.cloud.dto.BTerminalVo;
import com.sanxin.cloud.enums.RandNumType;
import com.sanxin.cloud.netty.enums.AppCommandEnums;
import com.sanxin.cloud.netty.enums.CommandEnums;
import com.sanxin.cloud.netty.hex.HexUtils;
import com.sanxin.cloud.netty.service.HandleService;
import com.sanxin.cloud.service.system.pay.HandleBatteryService;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 充电宝指令
 * @author arno
 * @version 1.0
 * @date 2019-09-17
 */
public class CommandUtils {

    private static Logger log = LoggerFactory.getLogger(CommandUtils.class);
    private static HandleService handleService = SpringBeanFactoryUtils.getApplicationContext().getBean(HandleService.class);
    private static HandleBatteryService handleBatteryService = SpringBeanFactoryUtils.getApplicationContext().getBean(HandleBatteryService.class);
    /**
     * 接收到机器的指令
     * @param content  机器发送过来的16进制
     * @return
     */
    public static String command(ChannelHandlerContext ctx, String content){
        String out_str="";//输入字符串 不需要16进制
        if(!StringUtils.isEmpty(content)){
            //已经是16进制
            String hex=content;
            if(!StringUtils.isEmpty(hex)){
                String [] hex_arr=hex.split(" ");
                String command=hex_arr[0];
                if(hex_arr.length>=2){
                    command=hex_arr[2];
                }
                CommandEnums enums=CommandEnums.getCommandFun(command);
                if(enums==null){
                    return "";
                }
                //生成token  4位数字，将数组转16进制
                String token= RandNumUtils.get(RandNumType.NUMBER,4);
                String hex_token=HexUtils.str2HexStr(token).replace(" ","");
                /**
                 * 获取命令代码处理不同逻辑
                 * 先组装16进制然后16进制转字符串发送到机柜
                 */
                log.info("接收文本内容16进制----------【"+hex+"】----------");
                log.info("接收指令----------【"+command+":"+enums.getRemark()+"】----------");
                switch (enums){
                    case x60:
                        // 机柜登陆及响应
                        //获取机器码长度 然后机器码长度从16转10进制
                        String box_length_str=hex_arr[15]+hex_arr[16];
                        Integer box_length=Integer.parseInt(box_length_str,16);
                        StringBuffer boxStr=new StringBuffer();
                        for(int i=1;i<=box_length;i++){
                            boxStr.append(hex_arr[16+i]);
                        }
                        String boxId=HexUtils.hexStr2Str(boxStr.toString()).trim();
                        log.info("登陆机器码----------【"+boxId+"】----------");
                        //将16进制转字符串
                        out_str="0008"+enums.getCommand()+"0100"+hex_token+"01";
                        out_str=HexUtils.hexStr2Str(out_str);
                        //登陆记录
                        NettySocketHolder.put(boxId,ctx);
                        // 登录成功——发送查询机柜充电宝数量指令
                        // ctx.channel().writeAndFlush(CommandUtils.sendCommand(CommandEnums.x6B.getCommand())).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                        break;
                    case x61:
                        // 心跳及响应
                        //心跳字节返回内容
                        out_str=HexUtils.hexStr2Str(hex.replace(" ",""));
                        break;
                    case x62:
                        // 查询机柜软件版本号及响应
                        String soft_ver_len_str = hex_arr[9] + hex_arr[10];
                        Integer soft_ver_len = Integer.parseInt(soft_ver_len_str, 16);
                        String soft_ver = String.join("", Arrays.copyOfRange(hex_arr, 11, soft_ver_len+11));
                        System.out.println("机柜软件版本号"+HexUtils.hexStr2Str(soft_ver).trim());
                        break;
                    case x63:
                        // 设置机柜服务器地址及响应
                        System.out.println("设置机柜服务器地址及响应");
                        break;
                    case x64:
                        // 查询机柜库存及响应
                        //获取到设备号
                        boxId=NettySocketHolder.get(ctx);
                        Integer terminalNum = Integer.parseInt(hex_arr[9], 16);
                        System.out.println("剩余充电宝个数"+terminalNum);
                        // 更新设备可租借数量
                        boolean updateNum = handleService.handleUpdateDeviceRentNum(boxId, terminalNum);
                        List<BTerminalVo> list = new ArrayList<>();
                        for (int i=1;i<hex_arr.length/10;i++) {
                            int num = 10*i;
                            String slot = hex_arr[num];
                            System.out.println("查询库存-充电宝槽位编号"+slot);
                            // 获取充电宝Id
                            String terminalId = String.join("", Arrays.copyOfRange(hex_arr, num+1, num+9));
                            terminalId = getTerminalId(terminalId);
                            System.out.println("查询库存-充电宝Id"+terminalId);
                            Integer level = Integer.parseInt(hex_arr[num + 9], 16);
                            System.out.println("查询库存-充电宝电量"+level);
                            BTerminalVo terminal = new BTerminalVo();
                            terminal.setTerminalId(terminalId);
                            // terminal.setStatus(TerminalStatusEnums.CHARGING.getStatus());
                            terminal.setSlot(slot);
                            // terminal.setdCode(boxId);
                            terminal.setLevel(level);
                            list.add(terminal);
                            // 操作-更新充电宝数据
                            // handleService.handleUpdateTerminal(terminal);
                        }
                        log.info("查询库存保存到redis_boxId"+boxId);
                        RedisUtils.getInstance().setTerminalByBoxId(boxId, list);
                        // 查询数据库电量最高的充电宝
                        // Map<String, String> map = handleService.getMostCharge(boxId);
                        // String slot = map.get("slot");
                        // // 发送指令借充电宝
                        // String cid = CNettySocketHolder.get(ctx);
                        // // 更新充电宝数据
                        // BDeviceTerminal terminal = new BDeviceTerminal();
                        // terminal.setTerminalId("terminalId");
                        // terminal.setUseCid(Integer.parseInt(cid));
                        // handleService.handleUpdateTerminal(terminal);
                        // CNettySocketHolder.remove(ctx);
                        // // 返回响应——借充电宝
                        // out_str = sendCommand(CommandEnums.x65.getCommand(), slot);
                        break;
                    case x65:
                        // 借充电宝及响应
                        String slot = hex_arr[9];
                        System.out.println("借充电宝响应  槽位"+slot);
                        String result = hex_arr[10];
                        System.out.println("借充电宝响应  结果"+result);
                        String terminalId = String.join("", Arrays.copyOfRange(hex_arr, 11, hex_arr.length));
                        terminalId = getTerminalId(terminalId);
                        System.out.println("借充电宝响应  充电宝Id"+terminalId);
                        boxId = NettySocketHolder.get(ctx);
                        System.out.println("借出充电宝的机柜id");
                        Integer status = Integer.parseInt(result, 16);
                        if (status == 1) {
                            // 借充电宝成功
                            handleBatteryService.handleLendBattery(boxId, terminalId, slot);
                        }
                        Integer useCid = handleService.queryCidByTerminalId(terminalId);
                        log.info("借充电宝发送响应cid"+useCid);
                        ChannelHandlerContext otherCtx = AppNettySocketHolder.get(useCid.toString());
                        otherCtx.channel().writeAndFlush(AppCommandUtils.sendCommand(AppCommandEnums.x10002.getCommand(), status.toString())).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                        break;
                    case x66:
                        // 归还充电宝
                        slot = hex_arr[9];
                        boxId=NettySocketHolder.get(ctx);
                        terminalId = String.join("", Arrays.copyOfRange(hex_arr, 10, hex_arr.length));
                        terminalId = getTerminalId(terminalId);
                        System.out.println("归还充电宝  槽位"+slot);
                        System.out.println("归还充电宝  充电宝编号"+terminalId);

                        // 充电宝表状态-改为已归还
                        // 查找该充电宝对应的订单，操作订单
                        // String status = handleService.handleReturnTerminal(slot, terminalId, ctx);
                        // System.out.println("归还充电宝  充电宝状态"+status);
                        RestResult returnResult = handleBatteryService.handleReturnBattery(boxId, slot, terminalId);
                        out_str = "0009"+enums.getCommand()+"0100"+hex_token+slot+returnResult.getData();
                        out_str = HexUtils.hexStr2Str(out_str);
                        // 给app发送指令
                        useCid = handleService.queryCidByTerminalId(terminalId);
                        log.info("归还充电宝cid"+useCid);
                        otherCtx = AppNettySocketHolder.get(useCid.toString());
                        if (returnResult.status) {
                            otherCtx.channel().writeAndFlush(AppCommandUtils.sendCommand(AppCommandEnums.x10003.getCommand(), "1", useCid.toString(), returnResult.getFlag(), terminalId)).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                        } else {
                            otherCtx.channel().writeAndFlush(AppCommandUtils.sendCommand(AppCommandEnums.x10003.getCommand(), "0")).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                        }
                        break;
                    case x67:
                        // 远程重启机柜及响应
                        System.out.println("远程重启机柜");
                        break;
                    case x68:
                        break;
                    case x69:
                        // 查询 ICCID
                        String ICCID_len_str = hex_arr[9]+hex_arr[10];
                        Integer ICCID_len = Integer.parseInt(ICCID_len_str, 16);
                        String ICCID = String.join("", Arrays.copyOfRange(hex_arr, 10, ICCID_len));
                        System.out.println("查询 ICCID"+HexUtils.hexStr2Str(ICCID));
                        break;
                    case x6A:
                        // 查询服务器地址
                        String address_length_str=hex_arr[9]+hex_arr[10];
                        Integer address_length=Integer.parseInt(address_length_str, 16);
                        String address = String.join("", Arrays.copyOfRange(hex_arr, 11, 11+address_length));
                        System.out.println("查询服务器地址  16进制"+address);
                        System.out.println("查询服务器地址"+HexUtils.hexStr2Str(address));
                        String port_len_str = hex_arr[11+address_length]+hex_arr[12+address_length];
                        Integer port_len = Integer.parseInt(port_len_str, 16);
                        String port = String.join("", Arrays.copyOfRange(hex_arr, 13+address_length, 13+address_length+port_len));
                        System.out.println("查询服务器地址端口  16进制"+port);
                        System.out.println("查询服务器地址端口"+HexUtils.hexStr2Str(port));
                        Integer heartbeat = Integer.parseInt(hex_arr[13+address_length+port_len], 16);
                        System.out.println("查询服务器地址 心跳间隔"+heartbeat);
                        break;
                    case x6B:
                        // 查询机柜库存充电宝数量
                        boxId = NettySocketHolder.get(ctx);
                        String terminal_num_str = hex_arr[9];
                        terminalNum = Integer.parseInt(terminal_num_str, 16);
                        handleService.handleSaveDevice(boxId, terminalNum);
                        System.out.println("查询机柜库存充电宝数量" + terminalNum);
                        break;
                    case x80:
                        // 强制弹出充电宝
                        slot = hex_arr[9];
                        result = hex_arr[10];
                        terminalId = String.join("", Arrays.copyOfRange(hex_arr, 11, hex_arr.length));
                        terminalId = getTerminalId(terminalId);
                        System.out.println("强制弹出槽位编号"+Integer.parseInt(slot, 16));
                        System.out.println("强制弹出结果"+Integer.parseInt(result, 16));
                        System.out.println("强制弹出充电宝Id"+terminalId);
                        if (Integer.parseInt(result, 16) == 1) {
                            // 强制弹出成功
                            // handleService.handleLendSuccess(terminalId);
                        }
                        break;
                }
            }
        }
        log.info("回复16进制内容----------【"+HexUtils.str2HexStr(out_str)+"】----------");
        return out_str;
    }

    /**
     * 发送指令
     * @param command
     * @return
     */
    public static String sendCommand(String command,String ... params){
        String out_str="";
        CommandEnums enums=CommandEnums.getCommandFun(command);
        //生成token  4位数字，将数组转16进制
        String token= RandNumUtils.get(RandNumType.NUMBER,4);
        String hex_token=HexUtils.str2HexStr(token).replace(" ","");
        log.info("发送指令----------【"+command+":"+enums.getRemark()+"】----------");
        switch (enums){
            case x62:
                // 查询机柜软件版本号及响应
                out_str="0007"+enums.getCommand()+"0133"+hex_token;
                out_str=HexUtils.hexStr2Str(out_str);
                break;
            case x63:
                // 设置机柜服务器地址及响应
                String address = "47.244.167.166";
                String port = "8086";
                // 服务器地址长度
                Integer addressLen = address.length();
                // 服务器地址长度16进制
                String addressLenHex = HexUtils.hexStr2Str(addressLen.toString());
                // 服务器地址16进制
                String addressHex = HexUtils.hexStr2Str(address);
                // 服务器端口长度
                Integer portLen = port.length();
                // 服务器端口长度16进制
                String portLenHex = HexUtils.hexStr2Str(portLen.toString());
                // 服务器端口16进制
                String portHex = HexUtils.hexStr2Str(port);
                // 心跳间隔
                Integer heartbeat = 30;
                // 心跳间隔16进制
                String heartbeatHex = HexUtils.hexStr2Str(heartbeat.toString());
                out_str="0007"+enums.getCommand()+"0133"+hex_token+addressLenHex+addressHex+portLenHex+portHex+heartbeatHex;
                out_str=HexUtils.hexStr2Str(out_str);
                break;
            case x64:
                // 查询机柜库存及响应
                out_str="0007"+enums.getCommand()+"0133"+hex_token;
                out_str=HexUtils.hexStr2Str(out_str);
                break;
            case x65:
                //发送接充电宝
                //将16进制转字符串
                out_str="0008"+enums.getCommand()+"0133"+hex_token+params[0];
                out_str=HexUtils.hexStr2Str(out_str);
                break;
            case x66:
                break;
            case x67:
                // 远程重启机柜及响应
                out_str="0007"+enums.getCommand()+"0133"+hex_token;
                out_str=HexUtils.hexStr2Str(out_str);
                break;
            case x68:
                break;
            case x69:
                // 查询 ICCID
                out_str="0007"+enums.getCommand()+"0133"+hex_token;
                out_str=HexUtils.hexStr2Str(out_str);
                break;
            case x6A:
                // 查询服务器地址
                out_str="0007"+enums.getCommand()+"0133"+hex_token;
                out_str=HexUtils.hexStr2Str(out_str);
                break;
            case x6B:
                // 查询机柜库存充电宝数量
                out_str="0007"+enums.getCommand()+"0133"+hex_token;
                out_str=HexUtils.hexStr2Str(out_str);
                break;
            case x80:
                //强制弹出充电宝
                //将16进制转字符串
                out_str="0008"+enums.getCommand()+"0133"+hex_token+params[0];
                out_str=HexUtils.hexStr2Str(out_str);
                break;
        }
        log.info("发送16进制内容----------【"+HexUtils.str2HexStr(out_str)+"】----------");
        return out_str;
    }

    /**
     * 获取充电宝Id-转码
     * @param terminalId
     * @return
     */
    public static String getTerminalId(String terminalId) {
        String result = "";
        String left = terminalId.substring(0, 8);
        result = HexUtils.hexStr2Str(left).trim();
        String right = terminalId.substring(8, 16);
        result += right;
        return result;
    }

}
