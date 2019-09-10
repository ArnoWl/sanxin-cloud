package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.Constant;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.config.redis.RedisCacheManage;
import com.sanxin.cloud.config.redis.RedisUtilsService;
import com.sanxin.cloud.entity.CAccount;
import com.sanxin.cloud.entity.CCustomer;
import com.sanxin.cloud.exception.ThrowJsonException;
import com.sanxin.cloud.mapper.CCustomerMapper;
import com.sanxin.cloud.service.CAccountService;
import com.sanxin.cloud.service.CCustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Arno
 * @since 2019-08-27
 */
@Service
public class CCustomerServiceImpl extends ServiceImpl<CCustomerMapper, CCustomer> implements CCustomerService {

    @Autowired
    private CAccountService cAccountService;
    @Autowired
    private RedisUtilsService redisUtilsService;

    @Override
    public void queryCustomerList(SPage<CCustomer> page, CCustomer customer) {
        QueryWrapper<CCustomer> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(customer.getNickName())) {
            wrapper.eq("nick_name", customer.getNickName());
        }
        if (customer.getStatus() != null) {
            wrapper.eq("status", customer.getStatus());
        }
        super.page(page, wrapper);
        for (CCustomer c : page.getRecords()) {
            QueryWrapper<CAccount> accountWrapper = new QueryWrapper<>();
            accountWrapper.eq("cid", c.getId());
            CAccount account = cAccountService.getOne(accountWrapper);
            c.setAccount(account);
            c.setPayWord(null);
            c.setPassWord(null);
        }
    }

    @Override
    public RestResult sendVerCode(String phone, String region) {
        /*CCustomer customer = this.baseMapper.selectOne(new QueryWrapper<CCustomer>().eq("phone", phone));
        if (customer == null) {
            throw new ThrowJsonException("用户不存在");
        }*/
        return null;
    }

    @Override
    public void doRegister(CCustomer customer) throws Exception {
        check(customer);
        CCustomer user = this.baseMapper.selectOne(new QueryWrapper<CCustomer>().eq("phone", customer.getPhone()));
        if (user != null) {
            throw new ThrowJsonException("不能重复注册");
        }
        // 根据手机号获取验证码
        String verCode = redisUtilsService.getKey(Constant.PHONE_VERCODE + customer.getPhone());
        if (!customer.getVerCode().equals(verCode)) {
            throw new ThrowJsonException("验证码不匹配");
        }
        baseMapper.insert(customer);

    }

    /**
     * 校验必填
     * @param customer
     */
    private void check(CCustomer customer) throws Exception {
        if (StringUtils.isEmpty(customer.getPhone())) {
            throw new ThrowJsonException("手机号不能为空");
        }
        if (StringUtils.isEmpty(customer.getPassWord())) {
            throw new ThrowJsonException("密码不能为空");
        }
        if (StringUtils.isEmpty(customer.getVerCode())) {
            throw new ThrowJsonException("验证码不能为空");
        }
    }
}
