package com.sanxin.cloud.service;

import com.sanxin.cloud.entity.SysAgreement;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统协议 服务类
 * </p>
 *
 * @author xiaoky
 * @since 2019-08-31
 */
public interface SysAgreementService extends IService<SysAgreement> {

    SysAgreement getByTypeAndLanguage(Integer type, String language);
}
