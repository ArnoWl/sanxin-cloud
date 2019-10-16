package com.sanxin.cloud.admin.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanxin.cloud.admin.api.service.StatisticsService;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.BDeviceTerminal;
import com.sanxin.cloud.enums.TerminalStatusEnums;
import com.sanxin.cloud.service.BDeviceTerminalService;
import com.sanxin.cloud.service.CAccountService;
import com.sanxin.cloud.service.OrderMainService;
import com.sanxin.cloud.service.SysCashDetailService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 首页数据
 * @author xiaoky
 * @date 2019-09-20
 */
@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private StatisticsService statisticsService;

    /**
     * 查询首页头部标签数据
     * @return
     */
    @GetMapping(value = "/labelMsg")
    public RestResult labelMsg() {
        Map<String, Object> map = new HashMap<>();
        // 充电宝数量
        int powerNum = statisticsService.countTerminalNum();
        map.put("powerNum", powerNum);
        BigDecimal orderMoney = statisticsService.sumOrderMoney();
        map.put("orderMoney", orderMoney);
        // 提现金额
        BigDecimal cashMoney = statisticsService.sumCashMoney();
        map.put("cashMoney", cashMoney);
        // 押金金额
        BigDecimal depositMoney = statisticsService.sumDepositMoney();
        map.put("depositMoney", depositMoney);
        return RestResult.success("", map);
    }

    /**
     * 首页提现统计
     * @return
     */
    @GetMapping(value = "/queryCashStatistics")
    public RestResult queryCashStatistics() {
        Map<String, Object> map = statisticsService.queryCashStatistics();
        return RestResult.success("", map);
    }
}
