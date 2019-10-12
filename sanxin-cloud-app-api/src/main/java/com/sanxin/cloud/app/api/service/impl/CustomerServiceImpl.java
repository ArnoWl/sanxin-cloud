package com.sanxin.cloud.app.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanxin.cloud.app.api.service.CustomerService;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.CAccount;
import com.sanxin.cloud.entity.CCustomer;
import com.sanxin.cloud.entity.CPushLog;
import com.sanxin.cloud.enums.CashTypeEnums;
import com.sanxin.cloud.mapper.CCustomerMapper;
import com.sanxin.cloud.mapper.CPushLogMapper;
import com.sanxin.cloud.service.CAccountService;
import com.sanxin.cloud.service.CCustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Arno
 * @since 2019-08-27
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CCustomerMapper, CCustomer> implements CustomerService {

    @Autowired
    private CCustomerMapper customerMapper;
    @Autowired
    private CPushLogMapper cPushLogMapper;

    /**
     * 个人中心(用户)
     *
     * @param cid
     * @return
     */
    @Override
    public RestResult getPersonCenter(Integer cid) {
        Map<String, String> map = new HashMap<>();
        CCustomer customer = customerMapper.selectById(cid);
        Integer integer = cPushLogMapper.selectCount(new QueryWrapper<CPushLog>().eq("target_id", cid).eq("target_type", CashTypeEnums.CUSTOMER.getId())
                .eq("reading", StaticUtils.STATUS_NO));
        map.put("nickName", customer.getNickName());
        map.put("headUrl", customer.getHeadUrl());
        map.put("number", integer.toString());
        return RestResult.success("map");
    }

}
