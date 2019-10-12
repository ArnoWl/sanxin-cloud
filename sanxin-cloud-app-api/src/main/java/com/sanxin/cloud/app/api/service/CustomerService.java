package com.sanxin.cloud.app.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.CCustomer;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Arno
 * @since 2019-08-27
 */
public interface CustomerService extends IService<CCustomer> {

    /**
     * 个人中心(用户)
     * @param cid
     * @return
     */
    RestResult getPersonCenter(Integer cid);
}
