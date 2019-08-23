package com.sanxin.cloud.admin.web.controller;


import com.sanxin.cloud.common.rest.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);


    @GetMapping("/test/order/{id}")
    public RestResult test(@PathVariable Long id){
        RestResult result=RestResult.success(id);
        return result;

    }

}
