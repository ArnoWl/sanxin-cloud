package com.sanxin.cloud.dto;

import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.AAdvertFind;
import lombok.Data;

import java.util.List;

@Data
public class AAdvertContentVO {
    //横向数据
    private List<AAdvertFind> transverse;
    //纵向分页
    private SPage<AAdvertFind> portrait;
    //广告轮播
    private List<AAdvertFind> banner;
}
