package com.sanxin.cloud.netty.properties;

import com.sanxin.cloud.common.random.RandNumUtils;
import com.sanxin.cloud.config.redis.RedisUtilsService;
import com.sanxin.cloud.enums.RandNumType;
import com.sanxin.cloud.netty.enums.CommandEnums;
import com.sanxin.cloud.netty.hex.HexUtils;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 充电宝指令
 * @author arno
 * @version 1.0
 * @date 2019-09-17
 */
public class CommandUtils {

    private static Logger log = LoggerFactory.getLogger(CommandUtils.class);
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
                        break;
                    case x61:
                        //心跳字节返回内容
                        out_str=HexUtils.hexStr2Str(hex.replace(" ",""));
                        break;
                    case x62:
                        break;
                    case x63:
                        break;
                    case x64:
                        //获取到设备号
                        boxId=NettySocketHolder.get(ctx);
                        break;
                    case x65:

                        break;
                    case x66:
                        break;
                    case x67:
                        break;
                    case x68:
                        break;
                    case x69:
                        break;
                    case x6A:
                        break;
                    case x6B:
                        break;
                    case x80:
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
                break;
            case x63:
                break;
            case x64:
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
                break;
            case x68:
                break;
            case x69:
                break;
            case x6A:
                break;
            case x6B:
                break;
            case x80:
                break;
        }
        log.info("发送16进制内容----------【"+HexUtils.str2HexStr(out_str)+"】----------");
        return out_str;
    }


    /**
     * 注入redis
     *
     * @return
     */
    private RedisUtilsService getRedisUtilsService() {
        return SpringBeanFactoryUtils.getApplicationContext().getBean(RedisUtilsService.class);
    }

}
