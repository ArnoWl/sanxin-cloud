package com.sanxin.cloud.service.system.pay;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.common.times.DateUtil;
import com.sanxin.cloud.entity.CAccount;
import com.sanxin.cloud.entity.CMarginDetail;
import com.sanxin.cloud.entity.CMoneyDetail;
import com.sanxin.cloud.entity.CPayLog;
import com.sanxin.cloud.enums.HandleTypeEnums;
import com.sanxin.cloud.enums.PayTypeEnums;
import com.sanxin.cloud.enums.ServiceEnums;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.service.CAccountService;
import com.sanxin.cloud.service.CPayLogService;
import io.netty.channel.ChannelOption;
import javafx.scene.chart.CategoryAxis;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 支付Service
 * @author xiaoky
 * @date 2019-10-09
 */
@Service
public class PayService {
    @Autowired
    private HandleAccountChangeService handleAccountChangeService;
    @Autowired
    private CPayLogService cPayLogService;
    @Autowired
    private CAccountService cAccountService;

    /**
     * 处理支付签名
     * @param log 支付记录数据
     * @return
     */
    public RestResult handleSign(CPayLog log) {
        //获取支付方式
        PayTypeEnums enums = PayTypeEnums.getEnums(log.getPayType());
        switch (enums) {
            case MONEY:
                //余额支付
                Integer handleType = log.getHandleType();
                String msg = "";
                //1.扣除余额
                if (log.getPayMoney() != null && BigDecimal.ZERO.compareTo(log.getPayMoney()) < 0) {
                    msg = handleAccountChangeService.insertCMoneyDetail(new CMoneyDetail(log.getCid(), handleType, StaticUtils.PAY_OUT, log.getPayCode(), log.getPayMoney(), "消费"));
                    if (!StringUtils.isEmpty(msg)) {
                        throw new ThrowJsonException(msg);
                    }
                }
                handlePayCallBack(log.getPayCode(), null);
                return RestResult.success("pay_success", null, log.getPayCode());
            default:
                return RestResult.fail("pay_type_not_found");
        }
    }

    /**
     * 处理支付回调
     * @param payCode   平台交易单号
     * @param transCode 第三方交易单号
     */
    public void handlePayCallBack(String payCode, String transCode) {
        QueryWrapper<CPayLog> wrapper = new QueryWrapper<>();
        wrapper.eq("pay_code", payCode);
        CPayLog cPayLog = cPayLogService.getOne(wrapper);
        if (cPayLog == null || FunctionUtils.isEquals(StaticUtils.STATUS_YES, cPayLog.getStatus())) {
            throw new ThrowJsonException("pay_log_not_found");
        }
        //2.根据版本号更新
        cPayLog.setStatus(StaticUtils.STATUS_YES);
        cPayLog.setTransCode(transCode);
        cPayLog.setPayTime(DateUtil.getInstance().currentDate());
        boolean flag = cPayLogService.updateById(cPayLog);
        if (!flag) {
            throw new ThrowJsonException("pay_log_processed");
        }
        ServiceEnums enums = ServiceEnums.getEnums(cPayLog.getServiceType());
        switch (enums) {
            case ORDER:
                // 订单处理
                break;
            case RECHARGE_DEPOSIT_MONEY:
                // 充值押金
                // 增加押金金额
                String msg = handleAccountChangeService.insertCDepositDetail(new CMarginDetail(cPayLog.getCid(), HandleTypeEnums.ORDER.getId(),
                        StaticUtils.PAY_OUT, cPayLog.getPayCode(), cPayLog.getPayMoney(), HandleTypeEnums.getName(HandleTypeEnums.ORDER.getId())));
                if (StringUtils.isNotEmpty(msg)) {
                    throw new ThrowJsonException(msg);
                }
                // 更新账户信息
                CAccount account = cAccountService.getByCid(cPayLog.getCid());
                account.setRechargeDeposit(StaticUtils.STATUS_YES);
                boolean result = cAccountService.updateById(account);
                if (!result) {
                    throw new ThrowJsonException(LanguageUtils.getMessage("data_exception"));
                }
                break;
            default:
                break;
        }
    }
}
