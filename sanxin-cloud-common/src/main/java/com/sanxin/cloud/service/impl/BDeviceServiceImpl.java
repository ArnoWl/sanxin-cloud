package com.sanxin.cloud.service.impl;

import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.BBusiness;
import com.sanxin.cloud.entity.BDevice;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.mapper.BDeviceMapper;
import com.sanxin.cloud.service.BBusinessService;
import com.sanxin.cloud.service.BDeviceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 店铺设备 服务实现类
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-06
 */
@Service
public class BDeviceServiceImpl extends ServiceImpl<BDeviceMapper, BDevice> implements BDeviceService {
    @Autowired
    private BBusinessService businessService;
    @Override
    public SPage<BDevice> queryDeviceList(SPage<BDevice> page, BDevice device) {
        return baseMapper.queryDeviceList(page, device);
    }

    @Override
    public BDevice getDeviceDetail(Integer id) {
        BDevice device = super.getById(id);
        if (device == null) {
            throw new ThrowJsonException("fail");
        }
        BBusiness business = businessService.getById(device.getBid());
        if (business != null) {
            device.setBusinessName(business.getNickName());
        }
        return device;
    }
}
