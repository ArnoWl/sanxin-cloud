package com.sanxin.cloud.dto;

import com.sanxin.cloud.entity.CMarginDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 押金提现
 */
@Data
public class CMarginVO {
    private BigDecimal margin;
    private List<CMarginDetail> list;
}
