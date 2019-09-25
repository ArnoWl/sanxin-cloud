package com.sanxin.cloud.app.api.controller;

import com.sanxin.cloud.common.http.HttpUtil;
import com.sanxin.cloud.common.rest.RestResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 充电宝Controller
 * @author xiaoky
 * @date 2019-09-24
 */
@RestController
@RequestMapping("/terminal")
public class TerminalController {

    /**
     * 借充电宝
     * @param code 柜机编号-扫码获得
     * @return
     */
    @GetMapping(value = "/lendTerminal")
    public RestResult lendTerminal(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("boxId", code);
        map.put("slot", "01");
        HttpUtil.getInstance().get("http://35.240.194.196:8003/netty/borrowBattery", map);
        return RestResult.success("success");
    }
}
