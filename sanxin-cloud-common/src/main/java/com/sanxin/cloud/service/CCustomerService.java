package com.sanxin.cloud.service;

import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.CCustomer;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Arno
 * @since 2019-08-27
 */
public interface CCustomerService extends IService<CCustomer> {

    /**
     * 查询用户列表
     * @param page 分页
     * @param customer 查询数据
     * @author xiaoky
     */
    void queryCustomerList(SPage<CCustomer> page, CCustomer customer);
}
