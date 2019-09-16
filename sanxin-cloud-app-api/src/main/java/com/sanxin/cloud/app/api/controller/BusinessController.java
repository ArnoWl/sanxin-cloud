package com.sanxin.cloud.app.api.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.BBusiness;
import com.sanxin.cloud.service.BBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business")
public class BusinessController extends BaseController{
    @Autowired
    private BBusinessService bBusinessService;

    /**
     * 根据经纬度分页查询周边商铺
     * @param current
     * @param size
     * @param latVal
     * @param longitude
     * @param province
     * @param city
     * @param district
     * @return
     * @throws Exception
     */
    public RestResult pageByShops(@RequestParam Integer current, @RequestParam Integer size, @RequestParam String latVal, @RequestParam String longitude, @RequestParam(required = false) String province, @RequestParam(required = false) String city, @RequestParam(required = false) String district) throws Exception {
        IPage<BBusiness> byShops = bBusinessService.findByShops(current, size, latVal, longitude, province, city, district);
        return RestResult.success("成功",byShops);
    }
}
