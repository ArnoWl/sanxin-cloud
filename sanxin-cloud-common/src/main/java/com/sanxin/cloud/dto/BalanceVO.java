package com.sanxin.cloud.dto;

import com.sanxin.cloud.entity.GiftHour;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购买时长礼包VO
 */
@Data
public class BalanceVO {

    private BigDecimal balance;

    private List<Integer> list;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "BalanceVO{" +
                "balance=" + balance +
                ", list=" + list +
                '}';
    }
}
