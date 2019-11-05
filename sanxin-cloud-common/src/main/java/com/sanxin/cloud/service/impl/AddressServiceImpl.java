package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.Address;
import com.sanxin.cloud.enums.LanguageEnums;
import com.sanxin.cloud.mapper.AddressMapper;
import com.sanxin.cloud.service.AddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 地址基础表 服务实现类
 * </p>
 *
 * @author Arno
 * @since 2019-10-28
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public RestResult getAddress(Integer parent) {
        String language = LanguageUtils.getLanguage();
        List<Address> list = null;
        try {
            list = addressMapper.selectList(new QueryWrapper<Address>().eq("parentid", parent));

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Address address : list) {
            if (language.equalsIgnoreCase("cn")) {
                address.setTranslation(address.getName());
            }
            if (language.equalsIgnoreCase("en")) {
                address.setTranslation(address.getNameEn());
            }
            if (language.equalsIgnoreCase("thai")) {
                address.setTranslation(address.getNameThai());
            }
        }
        return RestResult.success(list);
    }

    @Override
    public List<Address> queryAddressByPid(Integer pid) {
        String language = LanguageUtils.getLanguage();
        Address address = new Address();
        if (pid == null) {
            pid = StaticUtils.ADDRESS_COUNTRY;
        }
        address.setParentid(pid);
        List<Address> list = super.list(queryWrapper(address));
        for (Address a : list) {
            if (language.equalsIgnoreCase(LanguageEnums.CN.name())) {
                a.setTranslation(a.getName());
            }
            if (language.equalsIgnoreCase(LanguageEnums.EN.name())) {
                a.setTranslation(a.getNameEn());
            }
            if (language.equalsIgnoreCase(LanguageEnums.THAI.name())) {
                a.setTranslation(a.getNameThai());
            }
        }
        return list;
    }

    private QueryWrapper<Address> queryWrapper(Address address) {
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        // 父id非空取父id，为空取0
        if (address.getParentid() != null) {
            wrapper.eq("parentid",address.getParentid());
        }else{
            wrapper.eq("parentid", StaticUtils.ADDRESS_COUNTRY);
        }
        if (address.getLevel() != null) {
            wrapper.eq("level",address.getLevel());
        }
        if (FunctionUtils.isEquals(address.getStatus(), StaticUtils.STATUS_NO)) {
            wrapper.eq("status", address.getStatus());
        } else if (FunctionUtils.isEquals(address.getStatus(), StaticUtils.STATUS_YES)) {
            wrapper.eq("status", StaticUtils.STATUS_YES);
        } else {
            wrapper.eq("status", StaticUtils.STATUS_YES);
        }
        return wrapper;
    }
}
