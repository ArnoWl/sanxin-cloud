package com.sanxin.cloud.netty.properties;

import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author arno
 * @version 1.0
 * @date 2019-09-04
 */
public class NettySocketHolder {
    private static final Map<String, ChannelHandlerContext> MAP = new ConcurrentHashMap<>(16);

    private static String boxId="";


    public static void put(String id, ChannelHandlerContext channel) {
        MAP.put(id, channel);
    }

    public static ChannelHandlerContext get(String id) {
        return MAP.get(id);
    }

    public static String get(ChannelHandlerContext ctx) {
        MAP.entrySet().stream().filter(entry -> entry.getValue() == ctx).forEach(NettySocketHolder::accept);
        return boxId;
    }

    private static void accept(Map.Entry<String, ChannelHandlerContext> stringChannelHandlerContextEntry) {
        boxId = stringChannelHandlerContextEntry.getKey().toString();
    }

    public static Map<String, ChannelHandlerContext> getMAP() {
        return MAP;
    }

    public static void remove(ChannelHandlerContext channel) {
        MAP.entrySet().stream().filter(entry -> entry.getValue() == channel).forEach(entry -> MAP.remove(entry.getKey()));
    }

}
