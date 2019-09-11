package com.sanxin.cloud.service.impl;

import com.sanxin.cloud.entity.InfoAli;
import com.sanxin.cloud.mapper.InfoAliMapper;
import com.sanxin.cloud.service.InfoAliService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 支付宝配置表 服务实现类
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-09
 */
@Service
public class InfoAliServiceImpl extends ServiceImpl<InfoAliMapper, InfoAli> implements InfoAliService {

    @Override
    public InfoAli getInfoAli() {
        List<InfoAli> list = super.list();
        if (list != null && list.size()>0) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
