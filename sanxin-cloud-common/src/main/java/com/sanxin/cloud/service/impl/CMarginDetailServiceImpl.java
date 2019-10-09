package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.CCustomer;
import com.sanxin.cloud.entity.CMarginDetail;
import com.sanxin.cloud.mapper.CCustomerMapper;
import com.sanxin.cloud.mapper.CMarginDetailMapper;
import com.sanxin.cloud.service.CMarginDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 押金明细 服务实现类
 * </p>
 *
 * @author Arno
 * @since 2019-09-19
 */
@Service
public class CMarginDetailServiceImpl extends ServiceImpl<CMarginDetailMapper, CMarginDetail> implements CMarginDetailService {
    @Autowired
    private CCustomerMapper cCustomerMapper;

    /**
     *  用户押金明细列表
     * @param page
     * @param cMarginDetail
     */
    @Override
    public void queryCustomerDepositDetails(SPage<CMarginDetail> page, CMarginDetail cMarginDetail) {
        QueryWrapper wrapper = new QueryWrapper();
        if(cMarginDetail.getCid() != null){
            wrapper.eq("cid",cMarginDetail.getCid());
        }
        if(StringUtils.isNotBlank(cMarginDetail.getPayCode())){
            wrapper.like("pay_code",cMarginDetail.getPayCode());
        }
        if(cMarginDetail.getIsout() != null){
            wrapper.eq("isout",cMarginDetail.getIsout());
        }
        if(cMarginDetail.getType() != null){
            wrapper.eq("type",cMarginDetail.getType());
        }
        this.page(page,wrapper);
        for (CMarginDetail moneyDetail:page.getRecords()) {
            CCustomer cCustomer = cCustomerMapper.selectById(moneyDetail.getCid());
            moneyDetail.setNickName(cCustomer.getNickName());
        }
    }
}
