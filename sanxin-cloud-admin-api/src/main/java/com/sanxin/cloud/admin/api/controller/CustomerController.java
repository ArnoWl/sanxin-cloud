package com.sanxin.cloud.admin.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.common.pwd.PwdEncode;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.CCustomer;
import com.sanxin.cloud.service.CCustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户Controller
 * @author xiaoky
 * @date 2019-08-30
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CCustomerService customerService;

    /**
     * 查询用户列表
     * @param customer 用户查询数据
     * @return
     */
    @GetMapping(value = "/list")
    public RestResult queryCustomerList(SPage<CCustomer> page, CCustomer customer) {
        customerService.queryCustomerList(page, customer);
        return RestResult.success("", page);
    }

    /**
     * 操作用户状态有效/无效
     * @param id
     * @param status 状态
     * @return com.sanxin.cloud.common.rest.RestResult
     */
    @PostMapping(value = "/handleCustomerStatus")
    public RestResult handleCustomerStatus(Integer id, Integer status) {
        CCustomer customer = new CCustomer();
        customer.setId(id);
        customer.setStatus(status);
        boolean result = customerService.updateById(customer);
        if (!result) {
            return RestResult.fail("fail");
        }
        return RestResult.success("success");
    }

    /**
     * 修改用户交易密码
     * @param id
     * @param payWord 交易密码
     * @return
     */
    @PostMapping(value = "/handleUpdatePayWord")
    public RestResult handleUpdatePayWord(Integer id, String payWord) {
        CCustomer customer = new CCustomer();
        customer.setId(id);
        customer.setPayWord(PwdEncode.encodePwd(payWord));
        boolean result = customerService.updateById(customer);
        if (!result) {
            return RestResult.fail("fail");
        }
        return RestResult.success("success");
    }

    /**
     * 修改用户登录密码
     * @param id
     * @param passWord 登录密码
     * @return
     */
    @PostMapping(value = "/handleUpdatePassWord")
    public RestResult handleUpdatePassWord(Integer id, String passWord) {
        CCustomer customer = new CCustomer();
        customer.setId(id);
        customer.setPassWord(PwdEncode.encodePwd(passWord));
        boolean result = customerService.updateById(customer);
        if (!result) {
            return RestResult.fail("fail");
        }
        return RestResult.success("success");
    }
}
