package com.sanxin.cloud.app.api.controller;

import com.sanxin.cloud.app.api.remote.NettyRemote;
import com.sanxin.cloud.common.rest.RestResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 充电宝Controller
 * @author xiaoky
 * @date 2019-09-24
 */
@RestController
@RequestMapping("/terminal")
public class TerminalController {
    @Resource
    private NettyRemote nettyRemote;

    /**
     * 借充电宝
     * @param code 柜机编号-扫码获得
     * @return
     */
    @GetMapping(value = "/lendTerminal")
    public RestResult lendTerminal(String code) {
        nettyRemote.borrowBattery(code, "01");
        return RestResult.success("success");
    }
}
