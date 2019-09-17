package com.sanxin.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sanxin.cloud.entity.AAdvert;

/**
 * <p>
 * 广告商 服务类
 * </p>
 *
 * @author xiaoky
 * @since 2019-08-26
 */
public interface AAdvertService extends IService<AAdvert> {

    /**
     * 通过用户id查询数据
     * @param cid
     * @return
     */
    AAdvert getByCid(Integer cid);
}
