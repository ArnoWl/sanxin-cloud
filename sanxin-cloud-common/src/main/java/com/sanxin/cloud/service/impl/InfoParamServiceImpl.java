package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.entity.InfoParam;
import com.sanxin.cloud.mapper.InfoParamMapper;
import com.sanxin.cloud.service.InfoParamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 基础键值对配置表 服务实现类
 * </p>
 *
 * @author xiaoky
 * @since 2019-10-08
 */
@Service
public class InfoParamServiceImpl extends ServiceImpl<InfoParamMapper, InfoParam> implements InfoParamService {

    @Override
    public String getValueByCode(String code) {
        QueryWrapper<InfoParam> wrapper = new QueryWrapper<>();
        wrapper.eq("kcode", code);
        InfoParam param = super.getOne(wrapper);
        if (param != null) {
            return param.getKvalue();
        }
        return null;
    }
}
