package com.sanxin.cloud.app.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.app.api.service.DeviceService;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.dto.DeviceDetailVo;
import com.sanxin.cloud.entity.BBusiness;
import com.sanxin.cloud.entity.BDevice;
import com.sanxin.cloud.enums.DeviceStatusEnums;
import com.sanxin.cloud.enums.DeviceTypeEnums;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.service.BBusinessService;
import com.sanxin.cloud.service.BDeviceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 设备管理实现类
 * @author xiaoky
 * @date 2019-09-17
 */
@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private BDeviceService deviceService;
    @Autowired
    private BBusinessService businessService;

    @Override
    public int countByType(Integer bid, Integer type) {
        QueryWrapper<BDevice> wrapper = new QueryWrapper<>();
        wrapper.eq("bid", bid).eq("type", type).ge("status", DeviceStatusEnums.RUN.getStatus());
        return deviceService.count(wrapper);
    }

    @Override
    public void queryDeviceList(SPage<BDevice> page, Integer bid, Integer type, String key) {
        QueryWrapper<BDevice> wrapper = new QueryWrapper<>();
        wrapper.eq("bid", bid).ge("status", DeviceStatusEnums.RUN.getStatus())
                .like(StringUtils.isNotBlank(key), "code", key);;
        if (StringUtils.isNotBlank(key)) {
            wrapper.eq("type", type);
        }

        deviceService.page(page, wrapper);
        for (BDevice device : page.getRecords()) {
            device.setStatusName(DeviceStatusEnums.getName(device.getStatus()));
            device.setBid(null);
        }
    }

    @Override
    public DeviceDetailVo getDeviceDetail(Integer deviceId, Integer bid) {
        // 先校验店铺
        BBusiness business = businessService.validById(bid);
        // 查询设备信息
        BDevice device = deviceService.getById(deviceId);
        // 校验柜机信息
        if (!FunctionUtils.isEquals(device.getBid(), bid)) {
            throw new ThrowJsonException(LanguageUtils.getMessage("data_exception"));
        }
        // 信息校验通过——封装数据
        DeviceDetailVo vo = new DeviceDetailVo();
        BeanUtils.copyProperties(device, vo);
        vo.setTypeName(DeviceTypeEnums.getName(device.getType()));
        vo.setBusinessName(business.getNickName());
        vo.setAddressDetail(business.getAddressDetail());
        String hours = FunctionUtils.getHours(business.getStartDay(), business.getEndDay(), business.getStartTime(), business.getEndTime());
        vo.setHours(hours);
        return vo;
    }
}
