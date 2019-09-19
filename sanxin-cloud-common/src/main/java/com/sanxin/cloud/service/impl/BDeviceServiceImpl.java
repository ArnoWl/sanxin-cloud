package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.language.AdminLanguageStatic;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.BBusiness;
import com.sanxin.cloud.entity.BDevice;
import com.sanxin.cloud.enums.DeviceStatusEnums;
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

    @Override
    public BDevice getByCode(String code) {
        QueryWrapper<BDevice> wrapper = new QueryWrapper<>();
        wrapper.eq("code", code);
        return super.getOne(wrapper);
    }

    @Override
    public RestResult handleDeviceStatus(Integer deviceId, Integer status) {
        BDevice device = super.getById(deviceId);
        if (device == null) {
            return RestResult.fail("data_empty");
        }
        if (status != null && FunctionUtils.isEquals(device.getStatus(), status)) {
            return RestResult.fail(LanguageUtils.getMessage(AdminLanguageStatic.BASE_REPEAT_SUBMIT));
        }
        // 判断状态是否合法
        if (!DeviceStatusEnums.validStatus(status) || FunctionUtils.isEquals(status, DeviceStatusEnums.CONNECTION.getStatus())) {
            return RestResult.fail("data_exception");
        }
        device.setStatus(status);
        boolean result = super.updateById(device);
        if (!result) {
            return RestResult.fail("fail");
        }
        return RestResult.success("success");
    }
}
