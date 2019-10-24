package com.sanxin.cloud.admin.api.controller;

import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.common.times.DateUtil;
import com.sanxin.cloud.config.image.RestUploadFileInfo;
import com.sanxin.cloud.enums.EventEnums;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.admin.api.remote.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 公用配置
 * @author xiaoky
 * @date 2019-09-02
 */
@RestController
public class ConfigController {
    @Resource
    ImagesRemote imagesRemote;

    /**
     * 返回文件上传路径
     * @param file
     * @return
     */
    @PostMapping("/uploadOne")
    public RestResult uploadOne(@RequestParam(value = "file") MultipartFile file) {
        if (file == null) {
            return RestResult.fail("fail");
        }
        String path = "/" + DateUtil.toDateString(DateUtil.currentDate(), "yyyyMMdd");
        RestUploadFileInfo uploadFileInfo = imagesRemote.uploadImg(file, path);
        if (!uploadFileInfo.isStatus()) {
            throw new ThrowJsonException(uploadFileInfo.getDesc());
        }
        String url = uploadFileInfo.getServiceName() + uploadFileInfo.getFilePath() + uploadFileInfo.getFileName();
        return RestResult.success("success", url);
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
