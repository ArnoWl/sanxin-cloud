package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.entity.BAccount;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.mapper.BAccountMapper;
import com.sanxin.cloud.service.BAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 加盟商账户表 服务实现类
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-19
 */
@Service
public class BAccountServiceImpl extends ServiceImpl<BAccountMapper, BAccount> implements BAccountService {

    @Override
    public BAccount getByBid(Integer bid) {
        QueryWrapper<BAccount> wrapper = new QueryWrapper<>();
        wrapper.eq("bid", bid);
        BAccount bAccount = super.getOne(wrapper);
        if (bAccount == null) {
            throw new ThrowJsonException(LanguageUtils.getMessage("data_exception"));
        }
        return bAccount;
    }
}
