package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.CCustomer;
import com.sanxin.cloud.entity.CMarginDetail;
import com.sanxin.cloud.entity.CTimeDetail;
import com.sanxin.cloud.mapper.CCustomerMapper;
import com.sanxin.cloud.mapper.CTimeDetailMapper;
import com.sanxin.cloud.service.CTimeDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户时长明细表 服务实现类
 * </p>
 *
 * @author Arno
 * @since 2019-09-27
 */
@Service
public class CTimeDetailServiceImpl extends ServiceImpl<CTimeDetailMapper, CTimeDetail> implements CTimeDetailService {

    @Autowired
    private CCustomerMapper cCustomerMapper;

    /**
     * 时长明细列表
     * @param page
     * @param cTimeDetail
     */
    @Override
    public void queryCustomeTimeDetails(SPage<CTimeDetail> page, CTimeDetail cTimeDetail) {
        QueryWrapper wrapper = new QueryWrapper();
        if(cTimeDetail.getCid() != null){
            wrapper.eq("cid",cTimeDetail.getCid());
        }
        if(StringUtils.isNotBlank(cTimeDetail.getPayCode())){
            wrapper.like("pay_code",cTimeDetail.getPayCode());
        }
        if(cTimeDetail.getIsout() != null){
            wrapper.eq("isout",cTimeDetail.getIsout());
        }
        if(cTimeDetail.getType() != null){
            wrapper.eq("type",cTimeDetail.getType());
        }
        this.page(page,wrapper);
        for (CTimeDetail moneyDetail:page.getRecords()) {
            CCustomer cCustomer = cCustomerMapper.selectById(moneyDetail.getCid());
            moneyDetail.setNickName(cCustomer.getNickName());
        }
    }
}
