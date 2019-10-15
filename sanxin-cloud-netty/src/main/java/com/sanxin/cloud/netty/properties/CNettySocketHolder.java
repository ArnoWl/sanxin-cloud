package com.sanxin.cloud.netty.properties;

import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author arno
 * @version 1.0
 * @date 2019-09-04
 */
public class CNettySocketHolder {
    private static final Map<String, ChannelHandlerContext> MAP = new ConcurrentHashMap<>(16);

    private static String cid="";

    public static void put(String id, ChannelHandlerContext channel) {
        MAP.put(id, channel);
    }

    public static ChannelHandlerContext get(String id) {
        return MAP.get(id);
    }

    public static String get(ChannelHandlerContext ctx) {
        MAP.entrySet().stream().filter(entry -> entry.getValue() == ctx).forEach(CNettySocketHolder::accept);
        return cid;
    }

    private static void accept(Map.Entry<String, ChannelHandlerContext> stringChannelHandlerContextEntry) {
        cid = stringChannelHandlerContextEntry.getKey().toString();
    }

    public static Map<String, ChannelHandlerContext> getMAP() {
        return MAP;
    }

    public static void remove(ChannelHandlerContext channel) {
        MAP.entrySet().stream().filter(entry -> entry.getValue() == channel).forEach(entry -> MAP.remove(entry.getKey()));
    }

}
