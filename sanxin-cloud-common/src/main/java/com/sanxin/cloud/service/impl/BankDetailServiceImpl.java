package com.sanxin.cloud.service.impl;

import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.BankDetail;
import com.sanxin.cloud.entity.BankType;
import com.sanxin.cloud.mapper.BankDetailMapper;
import com.sanxin.cloud.service.BankDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanxin.cloud.service.BankTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 银行卡信息表 服务实现类
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-03
 */
@Service
public class BankDetailServiceImpl extends ServiceImpl<BankDetailMapper, BankDetail> implements BankDetailService {
    @Autowired
    private BankTypeService bankTypeService;

    /**
     * 检验银行卡信息
     * @param bankDetail
     * @return
     */
    private RestResult validBankMsg(BankDetail bankDetail) {
        if (StringUtils.isBlank(bankDetail.getRealName())) {
            return RestResult.fail("bank_name_empty");
        }
        if (bankDetail.getCardType() == null) {
            return RestResult.fail("card_type_empty");
        }
        if (StringUtils.isBlank(bankDetail.getCardNo())) {
            return RestResult.fail("card_no_empty");
        }
        if (StringUtils.isBlank(bankDetail.getBankCard())) {
            return RestResult.fail("bank_card_empty");
        }
        if (bankDetail.getTypeId() == null) {
            return RestResult.fail("bank_type_empty");
        }
        BankType bankType = bankTypeService.getById(bankDetail.getTypeId());
        if (bankType == null) {
            return RestResult.fail("fail");
        }
        if (StringUtils.isBlank(bankDetail.getPhone())) {
            return RestResult.fail("bank_phone_empty");
        }
        if (StringUtils.isBlank(bankDetail.getEmail())) {
            return RestResult.fail("bank_email_empty");
        }
        bankDetail.setLogo(bankType.getLogo());
        bankDetail.setBankName(bankType.getBankName());
        boolean validEmail = FunctionUtils.validEmail(bankDetail.getEmail());
        if (!validEmail) {
            return RestResult.fail("bank_email_error");
        }
        return RestResult.success("");
    }

    @Override
    public RestResult addBankDetail(BankDetail bankDetail) {
        // 银行卡是否为泰国本地卡-还未判断

        // 校验银行卡信息
        RestResult valid = validBankMsg(bankDetail);
        if (!valid.status) {
            return valid;
        }
        boolean result = super.save(bankDetail);
        if (result) {
            return RestResult.success("success");
        }
        return RestResult.fail("fail");
    }

    @Override
    public RestResult updateBankDetail(BankDetail bankDetail) {
        // 校验银行卡信息
        RestResult valid = validBankMsg(bankDetail);
        if (!valid.status) {
            return valid;
        }
        // 校验通过-将targetId和type置为null,该数据是不可修改数据
        bankDetail.setTargetId(null);
        bankDetail.setType(null);
        boolean result = super.updateById(bankDetail);
        if (result) {
            return RestResult.success("success");
        }
        return RestResult.fail("fail");
    }
}
