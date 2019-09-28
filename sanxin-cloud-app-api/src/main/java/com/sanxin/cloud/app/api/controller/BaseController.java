package com.sanxin.cloud.app.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.sanxin.cloud.app.api.remote.ImagesRemote;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.common.times.DateUtil;
import com.sanxin.cloud.config.image.RestUploadFileInfo;
import com.sanxin.cloud.exception.ThrowJsonException;
import jdk.nashorn.internal.scripts.JS;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 基础Controller
 * @author xiaoky
 * @date 2019-09-26
 */
@RestController
@RequestMapping("/base")
public class BaseController {
    @Resource
    ImagesRemote imagesRemote;

    /**
     * 上传图片接口
     * @param file
     * @return
     */
    @RequestMapping("/uploadImg")
    public RestResult uploadImg(@RequestParam(value = "file")MultipartFile file) {
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
     * 上传多张图片
     * @param files
     * @return
     */
    @RequestMapping("/uploadImgMultiple")
    public RestResult uploadImgMultiple(@RequestParam(value = "files")MultipartFile[] files) {
        if (files == null) {
            return RestResult.fail("fail");
        }
        String path = "/" + DateUtil.toDateString(DateUtil.currentDate(), "yyyyMMdd");
        JSONArray array = new JSONArray();
        for (MultipartFile file : files) {
            RestUploadFileInfo uploadFileInfo = imagesRemote.uploadImg(file, path);
            if (!uploadFileInfo.isStatus()) {
                throw new ThrowJsonException(uploadFileInfo.getDesc());
            }
            String url = uploadFileInfo.getServiceName() + uploadFileInfo.getFilePath() + uploadFileInfo.getFileName();
            array.add(url);
        }

        return RestResult.success("success", array.toJSONString());
    }

    @RequestMapping(value = "/uploadQR")
    public RestResult uploadQR() {
        String path = "/" + DateUtil.toDateString(DateUtil.currentDate(), "yyyyMMdd");
        RestUploadFileInfo uploadFileInfo = imagesRemote.uploadQR(path, "https://www.baidu.com/");
        if (!uploadFileInfo.isStatus()) {
            throw new ThrowJsonException(uploadFileInfo.getDesc());
        }
        String url = uploadFileInfo.getServiceName() + uploadFileInfo.getFilePath() + uploadFileInfo.getFileName();
        return RestResult.success("success", url);
    }
}
