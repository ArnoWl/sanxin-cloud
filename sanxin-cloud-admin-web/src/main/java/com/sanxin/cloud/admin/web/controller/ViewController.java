package com.sanxin.cloud.admin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author arno
 * @version 1.0
 * @date 2019-08-22
 */
@Controller
public class ViewController {

    @RequestMapping("/index")
    public String toPage(){
        System.out.print(111);
        return "/index";
    }

    @RequestMapping("/login")
    public String login(){
        System.out.print(111);
        return "/pages/login";
    }
}
