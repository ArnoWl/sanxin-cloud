package com.sanxin.cloud.service.system.pay;

import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.pwd.PwdEncode;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.CCustomer;
import com.sanxin.cloud.entity.CPayLog;
import com.sanxin.cloud.entity.OrderMain;
import com.sanxin.cloud.enums.HandleTypeEnums;
import com.sanxin.cloud.enums.OrderStatusEnums;
import com.sanxin.cloud.enums.PayTypeEnums;
import com.sanxin.cloud.enums.ServiceEnums;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.service.CCustomerService;
import com.sanxin.cloud.service.CPayLogService;
import com.sanxin.cloud.service.OrderMainService;
import com.sanxin.cloud.service.system.login.LoginTokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单支付处理Service
 * @author xiaoky
 * @date 2019-10-10
 */
@Service
public class PayOrderService {
    @Autowired
    private LoginTokenService loginTokenService;
    @Autowired
    private PayService payService;
    @Autowired
    private CCustomerService customerService;
    @Autowired
    private OrderMainService orderMainService;
    @Autowired
    private CPayLogService cPayLogService;

    /**
     * 处理订单支付(归还充电宝之后)
     * @param orderCode 订单编号
     * @param payWord 选择余额支付
     * @param payType 支付方式
     * @param payChannel 支付渠道
     * @return RestResult
     */
    public RestResult handleOrderPay(String orderCode, String payWord, Integer payType, Integer payChannel) {
        // 用户信息
        String token = BaseUtil.getUserToken();
        Integer cid = loginTokenService.validLoginCid(token);
        CCustomer customer = customerService.getById(cid);
        if (customer == null) {
            return RestResult.fail("register_user_empty");
        }
        // 订单信息
        OrderMain orderMain = orderMainService.getByOrderCode(orderCode);
        if (orderMain == null || !FunctionUtils.isEquals(orderMain.getCid(), cid)) {
            return RestResult.fail("data_exception");
        }
        // 判断订单状态——使用中才能支付
        if (FunctionUtils.isEquals(orderMain.getOrderStatus(), OrderStatusEnums.USING.getId())) {
            return RestResult.fail("order_pay_not_use_status");
        }
        if (FunctionUtils.isEquals(orderMain.getOrderStatus(), OrderStatusEnums.OVER.getId())) {
            return RestResult.fail("order_pay_not_over_status");
        }
        // 余额支付判断支付密码
        if (FunctionUtils.isEquals(payType, PayTypeEnums.MONEY.getId())) {
            // 判断是否余额支付——余额支付需要校验密码
            if (StringUtils.isBlank(payWord)) {
                return RestResult.fail("pay_word_empty");
            }
            String encryPayword = PwdEncode.encodePwd(payWord);
            if (!encryPayword.equals(customer.getPayWord())) {
                if (StringUtils.isBlank(customer.getPayWord())) {
                    return RestResult.fail("not_set_pay_word", null, "1");
                } else {
                    return RestResult.fail("pay_word_error");
                }
            }
        }
        // 支付记录
        CPayLog log = new CPayLog();
        log.setCid(cid);
        log.setPayType(payType);
        log.setPayChannel(payChannel);
        log.setPayMoney(orderMain.getPayMoney());
        log.setHandleType(HandleTypeEnums.ORDER.getId());
        log.setServiceType(ServiceEnums.ORDER.getId());
        log.setPayCode(orderMain.getPayCode());
        boolean flag = cPayLogService.save(log);
        if (!flag) {
            throw new ThrowJsonException(LanguageUtils.getMessage("pay_log_create_fail"));
        }
        return payService.handleSign(log);
    }
}
