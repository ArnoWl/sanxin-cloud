package com.sanxin.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.CFeedbackLog;
import com.sanxin.cloud.entity.CPushLog;
import com.sanxin.cloud.mapper.CFeedbackLogMapper;
import com.sanxin.cloud.service.CFeedbackLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 故障反馈 服务实现类
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-11
 */
@Service
public class CFeedbackLogServiceImpl extends ServiceImpl<CFeedbackLogMapper, CFeedbackLog> implements CFeedbackLogService {

    @Autowired
    private CFeedbackLogMapper feedbackLogMapper;

    @Override
    public IPage<CFeedbackLog> queryFaultFeedback(SPage<CFeedbackLog> page, Integer bid) {
        IPage<CFeedbackLog> list = feedbackLogMapper.selectPage(page, new QueryWrapper<CFeedbackLog>().eq("bid", bid));
        for (CFeedbackLog record : list.getRecords()) {
            String[] split = record.getBackUrl().split(",");
            List urlList=new ArrayList();
            for (int i = 0; i <split.length ; i++) {
                urlList.add(split[i]);
            }
            record.setUrl(urlList);
        }
        return list;
    }
}
