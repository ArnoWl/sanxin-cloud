package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.dto.CMarginVO;
import com.sanxin.cloud.dto.MoneyDetailVO;
import com.sanxin.cloud.dto.UserTimeVO;
import com.sanxin.cloud.entity.*;
import com.sanxin.cloud.mapper.*;
import com.sanxin.cloud.service.CAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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
    @Autowired
    private CMarginDetailMapper marginDetailMapper;
    @Autowired
    private CAccountMapper accountMapper;
    @Autowired
    private BankDetailMapper bankDetailMapper;
    @Autowired
    private CMoneyDetailMapper moneyDetailMapper;
    @Autowired
    private CTimeDetailMapper timeDetailMapper;


    /**
     * 我的押金
     * @param cid
     * @return
     */
    @Override
    public RestResult queryMyDeposit(SPage<CMarginDetail> page, Integer cid) {
        CMarginVO marginVO=new CMarginVO();
        Page<CMarginDetail> list = marginDetailMapper.queryMyDepositList(page, cid);
        CAccount account = accountMapper.selectOne(new QueryWrapper<CAccount>().eq("cid", cid));
        marginVO.setList(list);
        marginVO.setMargin(account.getDeposit());
        return RestResult.success(marginVO);
    }

    /**
     * 我的钱包
     * @param cid
     * @return
     */
    @Override
    public RestResult queryMyPurse(Integer cid) {
        CAccount account = accountMapper.selectOne(new QueryWrapper<CAccount>().eq("cid", cid));
        Integer count = bankDetailMapper.selectCount(new QueryWrapper<BankDetail>().eq("target_id", cid));
        account.setCard(count);
        return RestResult.success(account);
    }

    /**
     * 余额明细
     * @param cid
     * @return
     */
    @Override
    public RestResult queryBalanceDetail(SPage<CMoneyDetail> page, Integer cid) {
        MoneyDetailVO moneyDetailVO=new MoneyDetailVO();
        Page<CMoneyDetail> list = moneyDetailMapper.queryBalanceDetail(page,cid);
        CAccount account = accountMapper.selectOne(new QueryWrapper<CAccount>().eq("cid", cid));
        moneyDetailVO.setList(list);
        moneyDetailVO.setBalance(account.getMoney());
        return RestResult.success(moneyDetailVO);
    }

    /**
     * 我要充值显示余额
     * @param cid
     * @return
     */
    @Override
    public RestResult getBalance(Integer cid) {
        CAccount account = accountMapper.selectOne(new QueryWrapper<CAccount>().eq("cid", cid));
        return RestResult.success(account.getMoney());
    }

    @Override
    public BigDecimal sumDepositMoney() {
        return baseMapper.sumDepositMoney();
    }

    /**
     * 用户时长明细
     * @param page
     * @param cid
     * @return
     */
    @Override
    public RestResult queryTimeDetail(SPage<CTimeDetail> page, Integer cid) {
        UserTimeVO userTimeVO=new UserTimeVO();
        SPage<CTimeDetail> list = timeDetailMapper.queryTimeDetail(page, cid);
        CAccount account = accountMapper.selectOne(new QueryWrapper<CAccount>().eq("cid", cid));
        userTimeVO.setList(list);
        userTimeVO.setTime(account.getHour());
        return RestResult.success(userTimeVO);
    }

    /**
     * 剩余时长
     * @param cid
     * @return
     */
    @Override
    public RestResult getTime(Integer cid) {
        CAccount account = accountMapper.selectOne(new QueryWrapper<CAccount>().eq("cid", cid));
        return RestResult.success(account.getHour());
    }

}
