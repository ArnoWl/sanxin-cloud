package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.dto.CMarginVO;
import com.sanxin.cloud.dto.MoneyDetailVO;
import com.sanxin.cloud.dto.UserTimeVO;
import com.sanxin.cloud.entity.*;
import com.sanxin.cloud.mapper.*;
import com.sanxin.cloud.service.CAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * 用户账户 服务实现类
 * </p>
 *
 * @author xiaoky
 * @since 2019-08-30
 */
@Service
public class CAccountServiceImpl extends ServiceImpl<CAccountMapper, CAccount> implements CAccountService {
    @Override
    public CAccount getByCid(Integer cid) {
        QueryWrapper<CAccount> wrapper = new QueryWrapper<>();
        wrapper.eq("cid", cid);
        CAccount cAccount = super.getOne(wrapper);
        return cAccount;
    }

}
