package com.sanxin.cloud.service;

import com.sanxin.cloud.entity.InfoAuto;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 定时任务时间表 服务类
 * </p>
 *
 * @author xiaoky
 * @since 2019-10-24
 */
public interface InfoAutoService extends IService<InfoAuto> {

    /**
     * 查询定时任务数据
     * @return
     */
    InfoAuto selectOne();
}
