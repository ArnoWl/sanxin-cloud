package com.sanxin.cloud.admin.api.controller;

import com.sanxin.cloud.common.http.HttpUtil;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.enums.EventEnums;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公用配置
 * @author xiaoky
 * @date 2019-09-02
 */
@RestController
public class ConfigController {

    /**
     * 返回文件上传路径
     * @param file
     * @return
     */
    @PostMapping("/uploadOne")
    public RestResult uploadOne(@RequestParam(value = "file") MultipartFile file) {
        String msg = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1567404165178&di=18d023d8dd3c541a2b52ee1c544219cf&imgtype=0&src=http%3A%2F%2Fimgup04.iefans.net%2Fiefans%2F2019-02%2F11%2F11%2F15498570716693_1.jpg";
        return RestResult.success("", msg);
    }

    /**
     * 查询事件类型
     * @return
     */
    @GetMapping(value = "/queryEventType")
    public RestResult queryEventType() {
        List<Map<String, Object>> map = EventEnums.queryMap();
        return RestResult.success("", map);
    }
}
