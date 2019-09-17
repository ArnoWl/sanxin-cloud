package com.sanxin.cloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.Constant;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.pwd.PwdEncode;
import com.sanxin.cloud.common.random.RandNumUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.config.redis.RedisCacheManage;
import com.sanxin.cloud.config.redis.RedisUtilsService;
import com.sanxin.cloud.entity.CAccount;
import com.sanxin.cloud.entity.CCustomer;
import com.sanxin.cloud.enums.RandNumType;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.mapper.CCustomerMapper;
import com.sanxin.cloud.service.CAccountService;
import com.sanxin.cloud.service.CCustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

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
