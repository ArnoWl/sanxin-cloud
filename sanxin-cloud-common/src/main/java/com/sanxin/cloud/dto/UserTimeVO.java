package com.sanxin.cloud.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanxin.cloud.entity.CTimeDetail;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserTimeVO {
    private BigDecimal time;
    private Page<CTimeDetail> list;

    public BigDecimal getTime() {
        return time;
    }

    public void setTime(BigDecimal time) {
        this.time = time;
    }

    public Page<CTimeDetail> getList() {
        return list;
    }

    public void setList(Page<CTimeDetail> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "UserTimeVO{" +
                "time=" + time +
                ", list=" + list +
                '}';
    }
}
