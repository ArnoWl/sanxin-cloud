package com.sanxin.cloud.app.api.service;

import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.AAdvertFind;


/**
 * 申请
 * @author xiaoky
 * @date 2019-09-16
 */
public interface MessageService {

    /**
     * 查询广告列表
     * @param page
     */
    RestResult queryAdvertList(SPage<AAdvertFind> page);

    /**
     * 首页广告弹窗
     * @return
     */
    RestResult queryHomeAdvert();


}
