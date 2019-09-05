package com.sanxin.cloud.service.impl;

import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.SysCashDetail;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.mapper.SysCashDetailMapper;
import com.sanxin.cloud.service.SysCashDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 余额提现记录表 服务实现类
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-03
 */
@Service
public class SysCashDetailServiceImpl extends ServiceImpl<SysCashDetailMapper, SysCashDetail> implements SysCashDetailService {

    @Override
    public RestResult handleCashStatus(Integer id, Integer status) {
        SysCashDetail cash = super.getById(id);
        if (status != null && FunctionUtils.isEquals(cash.getStatus(), StaticUtils.STATUS_SUCCESS)) {
            return RestResult.fail("该申请已通过，请勿重复提交");
        }
        if (status != null && FunctionUtils.isEquals(cash.getStatus(),StaticUtils.STATUS_FAIL)) {
            return RestResult.fail("该申请已驳回，请勿重复提交");
        }
        cash.setStatus(status);
        cash.setEndTime(new Date());
        boolean save = super.updateById(cash);
        if (!save) {
            throw new ThrowJsonException("审核失败");
        }
        // 如果是驳回,将金额退还
        if (FunctionUtils.isEquals(cash.getStatus(),StaticUtils.STATUS_FAIL)) {

        } else if(FunctionUtils.isEquals(cash.getStatus(),StaticUtils.STATUS_SUCCESS)){

        }
        return RestResult.success("审核操作成功");
    }
}
