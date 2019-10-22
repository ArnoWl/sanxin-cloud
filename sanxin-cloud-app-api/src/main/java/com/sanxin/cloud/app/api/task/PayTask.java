package com.sanxin.cloud.app.api.task;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.entity.CPayLog;
import com.sanxin.cloud.enums.PayTypeEnums;
import com.sanxin.cloud.service.CPayLogService;
import com.sanxin.cloud.service.system.pay.PayService;
import com.sanxin.cloud.service.system.pay.scb.SCBPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author xiaoky
 * @date 2019-10-17
 */
@Component
public class PayTask {
    @Autowired
    private CPayLogService cPayLogService;
    @Autowired
    private SCBPayService scbPayService;
    @Autowired
    private PayService payService;

    /**
     * 定时查询SCB支付是否成功
     */
    @Scheduled(cron = "0/30 * * * * ? ")
    public void handleSCBPayAuto() {
        QueryWrapper<CPayLog> wrapper = new QueryWrapper<>();
        wrapper.eq("pay_type", PayTypeEnums.SCB_PAY.getId()).eq("status", StaticUtils.STATUS_NO);
        List<CPayLog> list = cPayLogService.list(wrapper);
        for (CPayLog log : list) {
            // JSONObject object = scbPayService.transactionRecord(log.getCid(), log.getTransCode());
            // if ("1".equals(object.getString("status"))) {
            //     Integer statusCode = -1;
            //     try {
            //         statusCode = Integer.parseInt(object.getString("statusCode"));
            //     } catch (NumberFormatException e) {
            //     }
            //     // 支付成功
            //     if (FunctionUtils.isEquals(statusCode, 1)) {
            //         payService.handlePayCallBack(log.getPayCode(), log.getTransCode());
            //     } else if (statusCode > 1) {
            //         log.setStatus(2);
            //         cPayLogService.updateById(log);
            //     }
            // }
        }
    }
}
