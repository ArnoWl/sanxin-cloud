package com.sanxin.cloud.admin.api.controller;

import com.sanxin.cloud.admin.api.service.DeviceService;
import com.sanxin.cloud.common.language.AdminLanguageStatic;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.BDevice;
import com.sanxin.cloud.service.BDeviceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 设备管理Controller
 * @author xiaoky
 * @date 2019-09-06
 */
@RestController
@RequestMapping(value = "/device")
public class DeviceController {
    @Autowired
    private BDeviceService bDeviceService;
    @Autowired
    private DeviceService deviceService;

    /**
     * 查询设备列表
     * @return
     */
    @GetMapping(value = "/queryDeviceList")
    public RestResult queryDeviceList(SPage<BDevice> page, BDevice device) {
        SPage<BDevice> pageInfo = bDeviceService.queryDeviceList(page, device);
        return RestResult.success("", pageInfo);
    }

    /**
     * 处理设备状态
     * @param id
     * @param status 状态
     * @return
     */
    @PostMapping(value = "/handleDeviceStatus")
    public RestResult handleDeviceStatus(Integer id, Integer status) {
        BDevice device = bDeviceService.getById(id);
        if (status != null && FunctionUtils.isEquals(device.getStatus(), status)) {
            return RestResult.fail(LanguageUtils.getMessage(AdminLanguageStatic.BASE_REPEAT_SUBMIT));
        }
        RestResult result = bDeviceService.handleDeviceStatus(id, status);
        return result;
    }

    /**
     * 查询设备详细信息
     * @param id
     * @return
     */
    @GetMapping(value = "/getDeviceDetail")
    public RestResult getDeviceDetail(Integer id) {
        BDevice device = bDeviceService.getDeviceDetail(id);
        if (device == null) {
            return RestResult.fail(LanguageUtils.getMessage(AdminLanguageStatic.BASE_FAIL));
        }
        return RestResult.success("", device);
    }

    /**
     * 查询设备库存信息
     * @param id
     * @return
     */
    @GetMapping(value = "/getDeviceTerminalDetail")
    public RestResult getDeviceTerminalDetail(Integer id) {
        Map<String, Object> device = deviceService.getDeviceTerminalDetail(id);
        return RestResult.success("", device);
    }

    /**
     * 修改设备信息
     * @param device 设备信息
     * @return
     */
    @PostMapping("/handleEditDevice")
    public RestResult handleEditDevice(BDevice device) {
        if (StringUtils.isBlank(device.getCode())) {
            return RestResult.fail("请输入设备编号");
        }
        if (device.getType() == null) {
            return RestResult.fail("请选择设备类型");
        }
        if (device.getBid() == null) {
            return RestResult.fail("请选择所在门店");
        }
        boolean result = bDeviceService.saveOrUpdate(device);
        if (!result) {
            return RestResult.fail(LanguageUtils.getMessage(AdminLanguageStatic.BASE_FAIL));
        }
        return RestResult.success(LanguageUtils.getMessage(AdminLanguageStatic.BASE_SUCCESS));
    }
}
