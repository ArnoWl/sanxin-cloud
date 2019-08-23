package com.sanxin.cloud.admin.api.service;

import com.sanxin.cloud.entity.SysConfig;
import com.sanxin.cloud.mapper.SysConfigMapper;
import com.sanxin.cloud.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author arno
 * @version 1.0
 * @date 2019-08-23
 */
@Service
public class LoginService {


    @Autowired
    public ISysConfigService iSysConfigService;
//
    public List<SysConfig> queryList(){
        List<SysConfig> list=iSysConfigService.queryTest();
        return  list;
    }
}
