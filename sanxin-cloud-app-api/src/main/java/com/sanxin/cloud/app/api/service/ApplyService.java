package com.sanxin.cloud.app.api.service;

import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.AAdvert;
import com.sanxin.cloud.entity.AgAgent;
import com.sanxin.cloud.entity.BBusiness;
import com.sanxin.cloud.enums.CardTypeEnums;
import com.sanxin.cloud.service.AAdvertService;
import com.sanxin.cloud.service.AgAgentService;
import com.sanxin.cloud.service.BBusinessService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.management.resources.agent;

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
