package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanxin.cloud.entity.CFeedbackLog;
import com.sanxin.cloud.mapper.CFeedbackLogMapper;
import com.sanxin.cloud.service.CPushLogService;
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
public class CPushLogServiceImpl extends ServiceImpl<CPushLogMapper, CPushLog> implements CPushLogService{

    @Override
    public IPage<CPushLog> queryMyMessage(SPage<CPushLog> page, Integer cid) {
        IPage<CPushLog> iPage = baseMapper.selectPage(page, new QueryWrapper<CPushLog>().eq("cid", cid));
        return iPage;
    }
}
