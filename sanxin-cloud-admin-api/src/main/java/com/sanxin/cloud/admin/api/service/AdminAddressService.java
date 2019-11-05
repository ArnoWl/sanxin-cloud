package com.sanxin.cloud.admin.api.service;

import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.Address;
import com.sanxin.cloud.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 地址service
 * @author xiaoky
 * @date 2019-11-05
 */
@Service
public class AdminAddressService {
    @Autowired
    private AddressService addressService;

    /**
     * 处理新增地址
     * @param address
     * @return
     */
    public RestResult handleEditAddress(Address address) {
        Integer level = 1;
        // 先查找上级地址
        if (!FunctionUtils.isEquals(address.getParentid(), StaticUtils.ADDRESS_COUNTRY)) {
            Address parentAddr = addressService.getById(address.getParentid());
            if (parentAddr == null) {
                return RestResult.fail("data_exception");
            }
            level = parentAddr.getLevel() + 1;
        }
        address.setLevel(level);
        address.setStatus(StaticUtils.STATUS_YES);
        boolean save = addressService.save(address);
        if (save) {
            return RestResult.success("success");
        }
        return RestResult.fail("fail");
    }
}
