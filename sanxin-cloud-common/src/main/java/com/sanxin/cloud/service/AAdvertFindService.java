package com.sanxin.cloud.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.AAdvertContent;
import com.sanxin.cloud.entity.AAdvertFind;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 发现广告表 服务类
 * </p>
 *
 * @author Arno
 * @since 2019-10-18
 */
public interface AAdvertFindService extends IService<AAdvertFind> {

    void queryAdvertFindList(SPage<AAdvertFind> page);

    boolean saveOrUpdate(AAdvertFind advertFind);

}
