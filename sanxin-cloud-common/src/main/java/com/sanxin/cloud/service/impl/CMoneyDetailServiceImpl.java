package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.CCustomer;
import com.sanxin.cloud.entity.CMoneyDetail;
import com.sanxin.cloud.mapper.CCustomerMapper;
import com.sanxin.cloud.mapper.CMoneyDetailMapper;
import com.sanxin.cloud.service.CMoneyDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户商余额明细 服务实现类
 * </p>
 *
 * @author Arno
 * @since 2019-09-20
 */
@Service
public class CMoneyDetailServiceImpl extends ServiceImpl<CMoneyDetailMapper, CMoneyDetail> implements CMoneyDetailService {

    @Autowired
    private CCustomerMapper cCustomerMapper;

    /**
     * 用户金额明细列表
     * @param page
     * @param cMoneyDetail
     */
    @Override
    public void queryCustomerAmountDetails(SPage<CMoneyDetail> page, CMoneyDetail cMoneyDetail) {
        QueryWrapper<CMoneyDetail> wrapper = new QueryWrapper<>();
        if(cMoneyDetail.getCid() != null){
            wrapper.eq("cid",cMoneyDetail.getCid());
        }
        if(StringUtils.isNotBlank(cMoneyDetail.getPayCode())){
            wrapper.like("pay_code",cMoneyDetail.getPayCode());
        }
        if(cMoneyDetail.getType() != null){
            wrapper.eq("type",cMoneyDetail.getType());
        }
        if(cMoneyDetail.getIsout() != null){
            wrapper.eq("isout",cMoneyDetail.getIsout());
        }
        this.page(page,wrapper);
        for (CMoneyDetail moneyDetail:page.getRecords()) {
            CCustomer cCustomer = cCustomerMapper.selectById(moneyDetail.getCid());
            moneyDetail.setNickName(cCustomer.getNickName());
        }
    }
}
