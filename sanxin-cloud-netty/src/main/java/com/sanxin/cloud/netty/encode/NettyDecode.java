package com.sanxin.cloud.netty.encode;

import com.sanxin.cloud.netty.hex.HexUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author arno
 * @version 1.0
 * @date 2019-09-17
 */
public class NettyDecode extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        //创建字节数组,buffer.readableBytes可读字节长度
        byte[] b = new byte[buffer.readableBytes()];
        //复制内容到字节数组b
        buffer.readBytes(b);
        //字节数组转字符串
        String str = new String(b);
        out.add(HexUtils.bytesToHexString(b));
    }

}
