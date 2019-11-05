package com.sanxin.cloud.admin.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.admin.api.service.AdminAddressService;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.Address;
import com.sanxin.cloud.service.AddressService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 地址管理Service
 * @author xiaoky
 * @date 2019-10-30
 */
@RestController
@RequestMapping(value = "/address")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @Autowired
    private AdminAddressService adminAddressService;

    /**
     * 查询地址列表
     * @param page
     * @return
     */
    @GetMapping(value = "/addressList")
    public RestResult queryAddressList(SPage<Address> page, Address address) {
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        if (address.getParentid() == null) {
            address.setParentid(0);
        }
        if (address.getProId() != null) {
            address.setParentid(address.getProId());
        }
        if (address.getCityId() != null) {
            address.setParentid(address.getCityId());
        }
        wrapper.eq("parentid", address.getParentid());
        addressService.page(page, wrapper);
        return RestResult.success("success", page);
    }

    /**
     * 操作地址数据状态
     * @param id
     * @param status
     * @return
     */
    @PostMapping(value = "/handleAddressStatus")
    public RestResult handleAddressStatus(Integer id, Integer status) {
        Address address = new Address();
        address.setId(id);
        address.setStatus(status);
        boolean result = addressService.updateById(address);
        if (result) {
            return RestResult.success("success");
        }
        return RestResult.fail("fail");
    }

    /**
     * 根据上级查询
     * @param pid
     * @return
     */
    @GetMapping(value = "/queryAddressListByPid")
    public RestResult queryAddressListByPid(Integer pid) {
        List<Address> list = addressService.queryAddressByPid(pid);
        return RestResult.success("", list);
    }

    /**
     * 根据上级查询
     * @param address
     * @return
     */
    @PostMapping(value = "/editAddress")
    public RestResult editAddress(Address address) {
        RestResult valid = validAddressMsg(address);
        if (!valid.status) {
            return valid;
        }
        address.setParentid(null);
        address.setStatus(null);
        address.setLevel(null);
        boolean result = addressService.updateById(address);
        if (result) {
            return RestResult.success("success");
        }
        return RestResult.fail("fail");
    }

    /**
     * 根据上级新增地址
     * @param address
     * @return
     */
    @PostMapping(value = "/addAddress")
    public RestResult addAddress(Address address) {
        RestResult valid = validAddressMsg(address);
        if (!valid.status) {
            return valid;
        }
        RestResult result = adminAddressService.handleEditAddress(address);
        return result;
    }

    /**
     * 校验地址参数
     * @param address
     * @return
     */
    private RestResult validAddressMsg(Address address) {
        if (StringUtils.isBlank(address.getName())) {
            return RestResult.fail("请输入中文名称");
        }
        if (StringUtils.isBlank(address.getNameThai())) {
            return RestResult.fail("请输入泰文名称");
        }
        if (StringUtils.isBlank(address.getNameEn())) {
            return RestResult.fail("请输入英文名称");
        }
        return RestResult.success("success");
    }
}
