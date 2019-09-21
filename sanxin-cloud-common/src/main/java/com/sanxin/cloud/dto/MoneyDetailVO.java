package com.sanxin.cloud.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanxin.cloud.entity.CMoneyDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 押金提现
 */
@Data
public class MoneyDetailVO {
    private BigDecimal balance;
    private Page<CMoneyDetail> list;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Page<CMoneyDetail> getList() {
        return list;
    }

    public void setList(Page<CMoneyDetail> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "MoneyDetailVO{" +
                "balance=" + balance +
                ", list=" + list +
                '}';
    }
}
