package com.sanxin.cloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanxin.cloud.common.language.AdminLanguageStatic;
import com.sanxin.cloud.common.language.LanguageUtils;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.BBusiness;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.mapper.BBusinessMapper;
import com.sanxin.cloud.service.BBusinessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Arno
 * @since 2019-08-27
 */
@Service
public class BBusinessServiceImpl extends ServiceImpl<BBusinessMapper, BBusiness> implements BBusinessService {

    @Override
    public BBusiness selectById(Integer id) {
        BBusiness business = super.getById(id);
        if (business == null) {
            throw new ThrowJsonException(LanguageUtils.getMessage(AdminLanguageStatic.BASE_FAIL));
        }
        business.setPassWord(null);
        List<String> coverUrlList = new ArrayList<String>();
        if (StringUtils.isNotBlank(business.getCoverUrl())) {
            JSONArray jsonArray = JSON.parseArray(business.getCoverUrl());
            for (int i = 0; i<jsonArray.size(); i++) {
                String url = "";
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(jsonArray.get(i));
                coverUrlList.add(jsonObject.getString("url"));
            }
            business.setCoverUrlList(coverUrlList);
        }
        return business;
    }

    /**
     * 通过用户id查询数据
     * @param cid
     * @return
     */
    @Override
    public BBusiness getByCid(Integer cid) {
        QueryWrapper<BBusiness> wrapper = new QueryWrapper<>();
        wrapper.eq("cid", cid);
        return super.getOne(wrapper);
    }

}
