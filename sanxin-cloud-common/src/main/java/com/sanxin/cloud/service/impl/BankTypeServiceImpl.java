package com.sanxin.cloud.service.impl;

import com.sanxin.cloud.entity.BankType;
import com.sanxin.cloud.mapper.BankTypeMapper;
import com.sanxin.cloud.service.BankTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 银行卡类型表 服务实现类
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-03
 */
@Service
public class BankTypeServiceImpl extends ServiceImpl<BankTypeMapper, BankType> implements BankTypeService {

}
