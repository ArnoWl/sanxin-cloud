package com.sanxin.cloud.service;

import com.sanxin.cloud.entity.AAdvert;
import com.sanxin.cloud.entity.AgAgent;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 代理 服务类
 * </p>
 *
 * @author xiaoky
 * @since 2019-08-29
 */
public interface AgAgentService extends IService<AgAgent> {

    /**
     * 通过用户id查询数据
     * @param cid
     * @return
     */
    AgAgent getByCid(Integer cid);
}
