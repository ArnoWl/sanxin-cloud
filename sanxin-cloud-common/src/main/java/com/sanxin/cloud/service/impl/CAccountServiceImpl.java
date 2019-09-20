package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.dto.CMarginVO;
import com.sanxin.cloud.dto.MoneyDetailVO;
import com.sanxin.cloud.entity.BankDetail;
import com.sanxin.cloud.entity.CAccount;
import com.sanxin.cloud.entity.CMarginDetail;
import com.sanxin.cloud.entity.CMoneyDetail;
import com.sanxin.cloud.mapper.BankDetailMapper;
import com.sanxin.cloud.mapper.CAccountMapper;
import com.sanxin.cloud.mapper.CMarginDetailMapper;
import com.sanxin.cloud.mapper.CMoneyDetailMapper;
import com.sanxin.cloud.service.CAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * 我的押金
     * @param cid
     * @return
     */
    @Override
    public RestResult queryMyDeposit(Integer cid) {
        CMarginVO marginVO=new CMarginVO();
        List<CMarginDetail> list = marginDetailMapper.selectList(new QueryWrapper<CMarginDetail>().eq("cid", cid));
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
    public RestResult queryBalanceDetail(Integer cid) {
        MoneyDetailVO moneyDetailVO=new MoneyDetailVO();
        List<CMoneyDetail> list = moneyDetailMapper.selectList(new QueryWrapper<CMoneyDetail>().eq("cid", cid));
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
}
