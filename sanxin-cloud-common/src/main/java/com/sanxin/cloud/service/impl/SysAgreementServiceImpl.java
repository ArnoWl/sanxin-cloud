package com.sanxin.cloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.entity.SysAgreement;
import com.sanxin.cloud.enums.LanguageEnums;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.mapper.SysAgreementMapper;
import com.sanxin.cloud.service.SysAgreementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统协议 服务实现类
 * </p>
 *
 * @author xiaoky
 * @since 2019-08-31
 */
@Service
public class SysAgreementServiceImpl extends ServiceImpl<SysAgreementMapper, SysAgreement> implements SysAgreementService {

    @Override
    public SysAgreement getByTypeAndLanguage(Integer type, String language) {
        QueryWrapper<SysAgreement> wrapper = new QueryWrapper<>();
        wrapper.eq("type", type);
        SysAgreement agreement = super.getOne(wrapper);
        if (agreement == null) {
            throw new ThrowJsonException("Error");
        }
        // 返回对应语言的内容
        JSONObject object = JSONObject.parseObject(agreement.getTitle());
        agreement.setTitle(object.getString(language));
        if (LanguageEnums.CN.name().equalsIgnoreCase(language)) {
            agreement.setContent(agreement.getCnContent());
        } else if (LanguageEnums.EN.name().equalsIgnoreCase(language)) {
            agreement.setContent(agreement.getEnContent());
        } else if (LanguageEnums.THAI.name().equalsIgnoreCase(language)) {
            agreement.setContent(agreement.getThaiContent());
        }
        return agreement;
    }
}
