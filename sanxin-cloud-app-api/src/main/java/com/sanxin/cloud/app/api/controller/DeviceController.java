package com.sanxin.cloud.app.api.controller;

import com.sanxin.cloud.app.api.common.DeviceMapping;
import com.sanxin.cloud.app.api.service.DeviceService;
import com.sanxin.cloud.common.BaseUtil;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.login.LoginTokenService;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.dto.DeviceDetailVo;
import com.sanxin.cloud.entity.BDevice;
import com.sanxin.cloud.enums.DeviceStatusEnums;
import com.sanxin.cloud.enums.DeviceTypeEnums;
import com.sanxin.cloud.service.BBusinessService;
import com.sanxin.cloud.service.BDeviceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 设备管理Controller
 * @author xiaoky
 * @date 2019-09-17
 */
@RestController
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    private LoginTokenService loginTokenService;
    @Autowired
    private BBusinessService businessService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private BDeviceService bDeviceService;

    /**
     * 查询柜机数量
     * @return
     */
    @GetMapping(value = DeviceMapping.DEVICE_NUM)
    public RestResult getDeviceNum() {
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginTid(token);
        businessService.validById(bid);
        Integer large = deviceService.countByType(bid, DeviceTypeEnums.LARGE_CABINET.getType());
        Integer small = deviceService.countByType(bid, DeviceTypeEnums.SMALL_CABINET.getType());
        Map<String, Integer> map = new HashMap<>();
        map.put("large", large);
        map.put("small", small);
        return RestResult.success("", map);
    }

    /**
     * 查询柜机列表
     * @param page
     * @param type 类型，大柜机，小柜机
     * @param key 暂时无用，设计图上有一个搜索
     * @return
     */
    @GetMapping(value = DeviceMapping.QUERY_DEVICE_LIST)
    public RestResult queryDeviceList(SPage<BDevice> page, Integer type, String key) {
        deviceService.queryDeviceList(page, type, key);
        return RestResult.success("", page);
    }

    /**
     * 查询设备详情
     * @param deviceId
     * @return
     */
    @GetMapping(value = DeviceMapping.GET_DEVICE_DETAIL)
    public RestResult getDeviceDetail(Integer deviceId) {
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginTid(token);
        DeviceDetailVo device = deviceService.getDeviceDetail(deviceId, bid);
        return RestResult.success("", device);
    }

    /**
     * 操作设备状态
     * @param deviceId
     * @param status
     * @return
     */
    @PutMapping(value = DeviceMapping.HANDLE_DEVICE_STATUS)
    public RestResult handleDeviceStatus(Integer deviceId, Integer status) {
        // 校验店铺
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginTid(token);
        businessService.validById(bid);
        BDevice device = bDeviceService.getById(deviceId);
        if (!FunctionUtils.isEquals(bid, device.getBid())) {
            return RestResult.fail("data_exception");
        }
        RestResult result = bDeviceService.handleDeviceStatus(deviceId, status);
        return result;
    }

    /**
     * 编辑设备
     * @param deviceId
     * @param status
     * @return
     */
    @PutMapping(value = DeviceMapping.EDIT_DEVICE)
    public RestResult editDevice(Integer deviceId, String code, Integer status, Integer type) {
        // 校验店铺
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginTid(token);
        businessService.validById(bid);
        BDevice device = bDeviceService.getById(deviceId);
        if (device == null) {
            return RestResult.fail("data_empty");
        }
        // 校验店铺数据
        if (!FunctionUtils.isEquals(bid, device.getBid())) {
            return RestResult.fail("data_exception");
        }
        // 校验code是否为null
        if (StringUtils.isBlank(code)) {
            return RestResult.fail("device_code_empty");
        }
        // 判断状态是否合法
        if (!DeviceStatusEnums.validStatus(status) || FunctionUtils.isEquals(status, DeviceStatusEnums.CONNECTION.getStatus())) {
            return RestResult.fail("data_exception");
        }
        // 判断类型是否合法
        if (!DeviceTypeEnums.validType(type)) {
            return RestResult.fail("data_exception");
        }
        device.setCode(code);
        device.setStatus(status);
        device.setType(type);
        boolean result = bDeviceService.updateById(device);
        if (result) {
            return RestResult.success("success");
        }
        return RestResult.fail("fail");
    }

    /**
     * 添加设备
     * @param code 设备编号
     * @param status 状态
     * @param type 柜机类型
     * @return
     */
    @PostMapping(value = DeviceMapping.ADD_DEVICE)
    public RestResult addDevice(String code, Integer status, Integer type) {
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginTid(token);
        // 校验code是否为null
        if (StringUtils.isBlank(code)) {
            return RestResult.fail("device_code_empty");
        }
        BDevice queryDevice = bDeviceService.getByCode(code);
        if (queryDevice != null) {
            return RestResult.fail("device_code_exist");
        }
        // 判断状态是否合法
        if (!DeviceStatusEnums.validStatus(status) || FunctionUtils.isEquals(status, DeviceStatusEnums.CONNECTION.getStatus())) {
            return RestResult.fail("data_exception");
        }
        // 判断类型是否合法
        if (!DeviceTypeEnums.validType(type)) {
            return RestResult.fail("data_exception");
        }
        BDevice device = new BDevice();
        device.setCode(code);
        device.setStatus(status);
        device.setType(type);
        device.setBid(bid);
        boolean result = bDeviceService.save(device);
        if (result) {
            return RestResult.success("success");
        }
        return RestResult.fail("fail");
    }

    /**
     * 删除设备
     * @param deviceId
     * @return
     */
    @DeleteMapping(value = DeviceMapping.DELETE_DEVICE)
    public RestResult deleteDevice(Integer deviceId) {
        // 校验店铺
        String token = BaseUtil.getUserToken();
        Integer bid = loginTokenService.validLoginTid(token);
        businessService.validById(bid);
        BDevice device = bDeviceService.getById(deviceId);
        if (device == null) {
            return RestResult.fail("data_empty");
        }
        // 校验店铺数据
        if (!FunctionUtils.isEquals(bid, device.getBid())) {
            return RestResult.fail("data_exception");
        }
        boolean result = bDeviceService.removeById(deviceId);
        if (result) {
            return RestResult.success("success");
        }
        return RestResult.fail("fail");
    }
}
