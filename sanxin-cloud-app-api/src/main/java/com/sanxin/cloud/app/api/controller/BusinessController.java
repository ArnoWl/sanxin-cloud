package com.sanxin.cloud.app.api.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sanxin.cloud.app.api.common.MappingUtils;
import com.sanxin.cloud.app.api.service.BusinessService;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.entity.BBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business")
public class BusinessController {
    @Value("${spring.radius}")
    private Integer radius;

    @Autowired
    private BusinessService bBusinessService;

    /**
     * 根据经纬度分页查询周边商铺
     * @param current
     * @param size
     * @param latVal
     * @param longitude
     * @return
     * @throws Exception
     */
    @GetMapping(value = MappingUtils.NRARBY_BUSINESS)
    public RestResult pageByShops(@RequestParam Integer current, @RequestParam Integer size, String latVal, String longitude) throws Exception {
        IPage<BBusiness> byShops = bBusinessService.findByShops(current, size, latVal, longitude, radius);
        return RestResult.success("成功",byShops);
    }
}
