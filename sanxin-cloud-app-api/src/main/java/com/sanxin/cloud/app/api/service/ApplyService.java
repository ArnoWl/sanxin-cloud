package com.sanxin.cloud.app.api.service;

import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.AAdvert;
import com.sanxin.cloud.entity.AgAgent;
import com.sanxin.cloud.entity.BBusiness;

/**
 * 处理申请
 * @author xiaoky
 * @date 2019-09-16
 */
public interface ApplyService {

    /**
     * 处理广告申请
     * @param advert
     * @return
     */
    public RestResult handleAdvertApply(AAdvert advert);

    /**
     * 处理店铺申请
     * @param business
     * @return
     */
    public RestResult handleBusinessApply(BBusiness business);

    /**
     * 处理代理申请
     * @param agent
     * @return
     */
    public RestResult handleAgentApply(AgAgent agent);
}
