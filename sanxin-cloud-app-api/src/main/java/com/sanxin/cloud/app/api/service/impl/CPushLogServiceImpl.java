package com.sanxin.cloud.app.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanxin.cloud.app.api.service.CPushLogService;
import com.sanxin.cloud.common.rest.RestResult;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.CPushLog;
import com.sanxin.cloud.mapper.CPushLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author Arno
 * @since 2019-09-17
 */
@Service
public class CPushLogServiceImpl implements CPushLogService{

    @Autowired
    private CPushLogMapper pushLogMapper;

    @Override
    public RestResult queryMyMessage(SPage<CPushLog> page, Integer cid) {
        List<CPushLog> logList = pushLogMapper.selectList(new QueryWrapper<CPushLog>().eq("cid", cid));
        return RestResult.success(logList);
    }
}
