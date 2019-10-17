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
public class CPushLogServiceImpl extends ServiceImpl<CPushLogMapper, CPushLog> implements CPushLogService {

    @Autowired
    private CPushLogMapper pushLogMapper;


    @Override
    public IPage<CPushLog> queryMyMessage(SPage<CPushLog> page, Integer cid) {
        pushLogMapper.selectPage(page, new QueryWrapper<CPushLog>().eq("target_id", cid));
        return page;
    }

    /**
     * 已读消息
     * @param id 消息id
     * @param type 已读类型
     * @param cid 用户di
     * @return
     */
    @Override
    public RestResult readMessage(Integer id, Integer type, Integer cid) {
        switch (type) {
            case 1:
                CPushLog cPushLog = pushLogMapper.selectById(id);
                if (cPushLog != null) {
                    cPushLog.setReading(1);
                }
                int i = pushLogMapper.updateById(cPushLog);
                if (i > 0) {
                    return RestResult.success("success");
                }
                return RestResult.fail("fail");
            case 2:
                boolean b = pushLogMapper.updateList(cid);
                if (b) {
                    return RestResult.success("success");
                }
                return RestResult.fail("fail");
        }
        return RestResult.fail("fail");
    }
}
