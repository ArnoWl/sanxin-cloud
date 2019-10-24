package com.sanxin.cloud.service.impl;

import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.entity.InfoAuto;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.mapper.InfoAutoMapper;
import com.sanxin.cloud.service.InfoAutoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 定时任务时间表 服务实现类
 * </p>
 *
 * @author xiaoky
 * @since 2019-10-24
 */
@Service
public class InfoAutoServiceImpl extends ServiceImpl<InfoAutoMapper, InfoAuto> implements InfoAutoService {

    @Override
    public InfoAuto selectOne() {
        List<InfoAuto> list = super.list();
        if (list == null || list.size()<=0) {
            throw new ThrowJsonException(LanguageUtils.getMessage("data_exception"));
        }
        return list.get(1);
    }
}
