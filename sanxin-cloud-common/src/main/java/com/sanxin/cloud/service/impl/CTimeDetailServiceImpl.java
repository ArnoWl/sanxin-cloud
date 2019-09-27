package com.sanxin.cloud.service.impl;

import com.sanxin.cloud.config.pages.SPage;
import com.sanxin.cloud.entity.CTimeDetail;
import com.sanxin.cloud.mapper.CTimeDetailMapper;
import com.sanxin.cloud.service.CTimeDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户时长明细表 服务实现类
 * </p>
 *
 * @author Arno
 * @since 2019-09-27
 */
@Service
public class CTimeDetailServiceImpl extends ServiceImpl<CTimeDetailMapper, CTimeDetail> implements CTimeDetailService {
}
