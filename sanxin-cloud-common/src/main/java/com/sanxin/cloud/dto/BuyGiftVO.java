package com.sanxin.cloud.dto;

import com.sanxin.cloud.entity.GiftHour;
import lombok.Data;
import java.util.List;

/**
 * 购买时长礼包VO
 */
@Data
public class BuyGiftVO {

    private Integer hour;

    private List<GiftHour> list;

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public List<GiftHour> getList() {
        return list;
    }

    public void setList(List<GiftHour> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "BuyGiftVO{" +
                "hour=" + hour +
                ", list=" + list +
                '}';
    }
}
