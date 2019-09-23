package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.entity.BDeviceTerminal;
import com.sanxin.cloud.mapper.BDeviceTerminalMapper;
import com.sanxin.cloud.service.BDeviceTerminalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 充电宝信息表 服务实现类
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-17
 */
@Service
public class BDeviceTerminalServiceImpl extends ServiceImpl<BDeviceTerminalMapper, BDeviceTerminal> implements BDeviceTerminalService {

    @Override
    public BDeviceTerminal getTerminalById(String terminalId) {
        QueryWrapper<BDeviceTerminal> wrapper = new QueryWrapper<>();
        wrapper.eq("terminal_id", terminalId);
        return super.getOne(wrapper);
    }
}
