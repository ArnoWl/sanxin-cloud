package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.dto.CMarginVO;
import com.sanxin.cloud.entity.CAccount;
import com.sanxin.cloud.entity.CMarginDetail;
import com.sanxin.cloud.mapper.CAccountMapper;
import com.sanxin.cloud.mapper.CMarginDetailMapper;
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
}
