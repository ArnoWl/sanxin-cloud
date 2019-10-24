package com.sanxin.cloud.admin.api.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Netty服务调用
 * @author xiaoky
 * @date 2019-09-26
 */
@FeignClient(name= "sanxin-cloud-netty")
public interface NettyRemote {
    /**
     * 该抽象方法的注解、访问路径、方法签名要和提供服务的方法完全一致
     * @param boxId
     * @param slot
     * @return
     */
    @RequestMapping(value = "/netty/borrowBattery")
    void borrowBattery(@RequestParam(value = "boxId") String boxId, @RequestParam(value = "slot") String slot);
}
