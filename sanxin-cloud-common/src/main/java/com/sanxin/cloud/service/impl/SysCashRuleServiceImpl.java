package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.entity.BAccount;
import com.sanxin.cloud.entity.CAccount;
import com.sanxin.cloud.entity.SysCashRule;
import com.sanxin.cloud.enums.CashTypeEnums;
import com.sanxin.cloud.mapper.SysCashRuleMapper;
import com.sanxin.cloud.service.BAccountService;
import com.sanxin.cloud.service.CAccountService;
import com.sanxin.cloud.service.SysCashRuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanxin.cloud.service.system.login.LoginTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.provider.certpath.CertId;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-03
 */
@Service
public class SysCashRuleServiceImpl extends ServiceImpl<SysCashRuleMapper, SysCashRule> implements SysCashRuleService {
    @Autowired
    private CAccountService cAccountService;
    @Autowired
    private BAccountService bAccountService;

    @Override
    public SysCashRule getRuleByType(Integer type) {
        QueryWrapper<SysCashRule> wrapper = new QueryWrapper<>();
        wrapper.eq("role_type", type);
        return super.getOne(wrapper);
    }

    @Override
    public Map<String, Object> getCashRule(Integer type, Integer tid) {
        Map<String, Object> map = new HashMap<>();
        SysCashRule cashRule = getRuleByType(type);
        if (cashRule != null) {
            map.put("scale", cashRule.getScale());
            map.put("minVal", cashRule.getMinVal());
            map.put("maxVal", cashRule.getMaxVal());
            map.put("multiple", cashRule.getMultiple());
            map.put("taxOne", cashRule.getTaxOne());
            map.put("taxTwo", cashRule.getTaxTwo());
        }
        // 余额
        BigDecimal money = BigDecimal.ZERO;
        if (FunctionUtils.isEquals(type, CashTypeEnums.CUSTOMER.getId())) {
            CAccount account = cAccountService.getByCid(tid);
            if (account != null) {
                money = account.getMoney();
            }
        } else if (FunctionUtils.isEquals(type, CashTypeEnums.BUSINESS.getId())) {
            BAccount account = bAccountService.getByBid(tid);
            if (account != null) {
                money = account.getMoney();
            }
        }
        map.put("money", money);
        return map;
    }
}
