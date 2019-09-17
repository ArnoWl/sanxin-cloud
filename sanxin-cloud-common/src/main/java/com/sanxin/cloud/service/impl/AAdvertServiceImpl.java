package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanxin.cloud.entity.AAdvert;
import com.sanxin.cloud.mapper.AAdvertMapper;
import com.sanxin.cloud.service.AAdvertService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 广告商 服务实现类
 * </p>
 *
 * @author xiaoky
 * @since 2019-08-26
 */
@Service
public class AAdvertServiceImpl extends ServiceImpl<AAdvertMapper, AAdvert> implements AAdvertService {

    @Override
    public AAdvert getByCid(Integer cid) {
        QueryWrapper<AAdvert> wrapper = new QueryWrapper<>();
        wrapper.eq("cid", cid);
        return super.getOne(wrapper);
    }
}
