package com.sanxin.cloud.admin.api.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.common.StaticUtils;
import com.sanxin.cloud.common.times.DateUtil;
import com.sanxin.cloud.dto.QueryTimeDataVo;
import com.sanxin.cloud.enums.CashTypeEnums;
import com.sanxin.cloud.mapper.SysCashDetailMapper;
import com.sanxin.cloud.service.SysCashDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 数据统计service
 * @author xiaoky
 * @date 2019-09-21
 */
@Service
public class StatisticsService {
    @Autowired
    private SysCashDetailMapper sysCashDetailMapper;

    public Map<String, Object> queryCashStatistics() {
        JSONArray dateArray = new JSONArray();
        JSONArray customerCashArray = new JSONArray();
        JSONArray businessCashArray = new JSONArray();
        try {
            Integer type = StaticUtils.TIME_DAY;
            // 当前时间
            Date date = new Date();
            List<String> dateList = getDateListByType(type, date);
            QueryTimeDataVo vo = new QueryTimeDataVo();
            vo.setType(type);
            // 循环时间集
            for (int i = 0; i< dateList.size(); i++) {
                String data = dateList.get(i);
                vo.setDate(data);
                vo.setTargetType(CashTypeEnums.CUSTOMER.getId());
                BigDecimal customerCash = sysCashDetailMapper.sumCashMoneyByTime(vo);
                vo.setTargetType(CashTypeEnums.BUSINESS.getId());
                BigDecimal businessCash = sysCashDetailMapper.sumCashMoneyByTime(vo);
                businessCashArray.add(customerCash);
                customerCashArray.add(businessCash);
                dateArray.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject customerObj = new JSONObject();
        customerObj.put("name", "customer");
        customerObj.put("data", customerCashArray);
        JSONObject businessObj = new JSONObject();
        businessObj.put("name", "business");
        businessObj.put("data", businessCashArray);

        JSONArray array = new JSONArray();
        array.add(customerObj);
        array.add(businessObj);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", dateArray);
        map.put("series", array);
        return map;
    }

    /**
     * 获取月份日期数据
     * @param time     时间
     * @return 月份日期数据
     */
    public List<String> getDayDate(Date time) {
        List<String> dateList = new ArrayList<>();
        int maxDate = DateUtil.getMouthnum(time);
        String year = DateUtil.toDateString(time, "yyyy");
        String month = DateUtil.toDateString(time, "MM");
        String now = year + "-" + month;
        for (int i = 1; i <= maxDate; i++) {
            String day = i < 9 ? ("0" + i) : i + "";
            String nowtime = now + "-" + day;
            dateList.add(nowtime);
        }
        return dateList;
    }

    /**
     * 获取月份周数据
     * @param time 时间
     * @return 月份周数据
     */
    public List<String> getWeekDate(Date time) {
        List<String> weekList = new ArrayList<>();
        // 当前是第几周
        int locaknum = DateUtil.getLocalweek(time);
        // 本月有多少周
        int num = DateUtil.getLocalWeeknum(time);
        for (int i = 1; i <= num; i++) {
            if (i <= locaknum) {
                int a = locaknum - i;
                weekList.add(a+"");
            } else {
                int a = i - locaknum;
                weekList.add(-a+"");
            }
        }
        return weekList;
    }

    /**
     * 获取年份月份数据
     * @param time  时间
     * @return 月份数据
     */
    public List<String> getMonthDate (Date time) {
        List<String> dateList = new ArrayList<>();
        int maxDate = 12;
        String year = DateUtil.toDateString(time, "yyyy");
        for (int i = 1; i <= maxDate; i++) {
            String month = i < 9 ? ("0" + i) : i + "";
            String yearMonth = year + "-" + month + "-01";
            dateList.add(yearMonth);
        }
        return dateList;
    }

    /**
     * 通过类型获得不同的时间集
     * @param type 1 day, 2 week, 3 month
     * @param time 时间
     * @return List 时间集
     */
    public List<String> getDateListByType (Integer type, Date time) {
        List<String> dateList = new ArrayList<>();
        if (FunctionUtils.isEquals(type, StaticUtils.TIME_DAY)) {// 日期数据
            dateList = getDayDate(time);
        } else if (FunctionUtils.isEquals(type, StaticUtils.TIME_WEEK)) {// 周数据
            dateList = getWeekDate(time);
        } else if (FunctionUtils.isEquals(type, StaticUtils.TIME_MONTH)) {// 月数据
            dateList = getMonthDate(time);
        }
        return dateList;
    }
}
