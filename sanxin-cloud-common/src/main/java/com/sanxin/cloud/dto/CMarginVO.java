package com.sanxin.cloud.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanxin.cloud.entity.CMarginDetail;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 押金提现
 */
@Data
public class CMarginVO {
    private BigDecimal margin;
    private Page<CMarginDetail> list;
}
