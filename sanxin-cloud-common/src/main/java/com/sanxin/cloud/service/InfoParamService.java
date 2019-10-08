package com.sanxin.cloud.service;

import com.sanxin.cloud.entity.InfoParam;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 基础键值对配置表 服务类
 * </p>
 *
 * @author xiaoky
 * @since 2019-10-08
 */
public interface InfoParamService extends IService<InfoParam> {

    /**
     * 通过code获取值
     * @param code
     * @return
     */
    String getValueByCode(String code);
}
