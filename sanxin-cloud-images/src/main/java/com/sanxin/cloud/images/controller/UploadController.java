package com.sanxin.cloud.images.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.sanxin.cloud.images.common.RestUploadFileInfo;
import com.sanxin.cloud.images.util.Contant;
import com.sanxin.cloud.images.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * 上传Controller
 * @author xiaoky
 * @date 2019-09-26
 */
@RestController
public class UploadController {

    @Autowired
    private HttpServletRequest request;

    /**
     * 上传图片
     * @param file
     * @return
     */
    @PostMapping(value = "/uploadImg")
    public RestUploadFileInfo uploadImg(@RequestParam(value = "file") MultipartFile file, String path) throws FileNotFoundException {
        // 获取文件名称
        String fileName = file.getOriginalFilename();
        // 获取文件后缀
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        if ("jpeg".equals(ext.toLowerCase())) {
            ext = "jpg";
        }

        // 重新设置文件名称
        String name = Util.getTimeString();
        fileName = name + "." + ext;
        // 获取当前路径
        File targetFile = new File("");
        // 拼接上传路径
        targetFile = new File(targetFile.getAbsolutePath(), Contant.files+path);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        // 加上文件
        File newFile = new File(targetFile.getAbsolutePath(), fileName);
        try {
            // 将文件上传
            file.transferTo(newFile);
            RestUploadFileInfo restUploadFileInfo = new RestUploadFileInfo();
            restUploadFileInfo.setStatus(true);
            restUploadFileInfo.setDesc("success");
            restUploadFileInfo.setFilePath("/" + Contant.files + Util.getPath(path) + "/");
            restUploadFileInfo.setFileName(fileName);
            restUploadFileInfo.setServiceName(Util.getServiceName(request));
            boolean isImage = (ext.equals("jpg")) || (ext.equals("JPG")) || (ext.equals("png")) || (ext.equals("PNG")) || (ext.equals("JPEG")) || (ext.equals("jpeg")) || (ext.equals("gif")) || (ext.equals("GIF"));
            if (isImage) {
                //
            }
            return restUploadFileInfo;
        } catch (Exception e) {
            e.printStackTrace();
            RestUploadFileInfo restUploadFileInfo = new RestUploadFileInfo();
            restUploadFileInfo.setStatus(false);
            restUploadFileInfo.setDesc("fail");
            return restUploadFileInfo;
        }
    }

    /**
     * 上传图片多张
     * @param files
     * @return
     */
    @PostMapping(value = "/uploadImgMultiple")
    public String uploadImgMultiple(@RequestParam(value = "files") MultipartFile[] files, String path) throws FileNotFoundException {
        String urls = "";
        for (MultipartFile file : files) {
            RestUploadFileInfo uploadFileInfo = uploadImg(file, path);
            if (!uploadFileInfo.isStatus()) {
                continue;
            }
            String url = uploadFileInfo.getServiceName() + uploadFileInfo.getFilePath() + uploadFileInfo.getFileName();
            urls += url;
        }
        return urls;
    }

    /**
     * 上传二维码图片
     * @param path 图片路径
     * @param content 二维码内容
     * @return
     */
    @RequestMapping(value="/uploadQR")
    @ResponseBody
    public RestUploadFileInfo uploadQR(String path, String content) {
        RestUploadFileInfo result = new RestUploadFileInfo();
        try {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN,0);
            File file = new File("");
            String realPath = file.getAbsolutePath() + "/" + Contant.files+ Util.getPath(path) + "/";
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 340, 340, hints);
            String fileName = Util.getTimeString();
            File file1 = new File(realPath, fileName + ".png");
            if (!file1.exists()) {
                file1.mkdirs();
            }
            MatrixToImageWriter.writeToFile(bitMatrix, "png", file1);
            result.setStatus(true);
            result.setDesc("success");
            result.setFilePath(realPath);
            result.setFileName(fileName + ".png");
            result.setServiceName(Util.getServiceName(request));
        } catch (Exception e) {
            result.setStatus(false);
            result.setDesc("fail");
            return result;
        }
        return result;
    }
}
