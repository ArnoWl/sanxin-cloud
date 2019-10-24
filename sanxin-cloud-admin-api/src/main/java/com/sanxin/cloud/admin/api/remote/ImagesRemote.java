package com.sanxin.cloud.admin.api.remote;

import com.alibaba.fastjson.JSONArray;
import com.sanxin.cloud.config.image.RestUploadFileInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * Images服务调用
 * @author xiaoky
 * @date 2019-09-26
 */
@FeignClient(name = "sanxin-cloud-images", configuration = FeignMultipartSupportConfig.class)
public interface ImagesRemote {
    /**
     * 该抽象方法的注解、访问路径、方法签名要和提供服务的方法完全一致
     * MultipartFile 参数一定要用@RequestPart注解
     * @param file
     * @path path 文件上传路径
     * @return
     */
    @PostMapping(value = "/uploadImg",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    RestUploadFileInfo uploadImg(@RequestPart(value = "file") MultipartFile file, @RequestParam(value = "path") String path);
    /**
     * 上传生成二维码
     * @param path
     * @param content
     * @return
     */
    @PostMapping(value = "/uploadQR")
    RestUploadFileInfo uploadQR(@RequestParam(value = "path") String path, @RequestParam(value = "content") String content);

    /**
     * 多图上传
     * @param files
     * @param path
     * @return
     */
    @PostMapping(value = "/uploadImgMultiple",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    JSONArray uploadImgMultiple(@RequestPart(value = "files") MultipartFile[] files, @RequestParam(value = "path") String path);
}
