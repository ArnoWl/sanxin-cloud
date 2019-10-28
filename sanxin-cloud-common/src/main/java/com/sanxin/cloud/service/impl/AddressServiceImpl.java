package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.Address;
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
}
