package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.CAccount;
import com.sanxin.cloud.entity.CCustomer;
import com.sanxin.cloud.mapper.CCustomerMapper;
import com.sanxin.cloud.service.CAccountService;
import com.sanxin.cloud.service.CCustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Arno
 * @since 2019-08-27
 */
@Service
public class CCustomerServiceImpl extends ServiceImpl<CCustomerMapper, CCustomer> implements CCustomerService {

    @Autowired
    private CAccountService cAccountService;
    @Override
    public void queryCustomerList(SPage<CCustomer> page, CCustomer customer) {
        QueryWrapper<CCustomer> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(customer.getNickName())) {
            wrapper.eq("nick_name", customer.getNickName());
        }
        if (customer.getStatus() != null) {
            wrapper.eq("status", customer.getStatus());
        }
        super.page(page, wrapper);
        for (CCustomer c : page.getRecords()) {
            QueryWrapper<CAccount> accountWrapper = new QueryWrapper<>();
            accountWrapper.eq("cid", c.getId());
            CAccount account = cAccountService.getOne(accountWrapper);
            c.setAccount(account);
            c.setPayWord(null);
            c.setPassWord(null);
        }
    }
}
