package com.sanxin.cloud.service;

import com.sanxin.cloud.entity.InfoAli;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 支付宝配置表 服务类
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-09
 */
public interface InfoAliService extends IService<InfoAli> {
    InfoAli getInfoAli();
}
