package com.sanxin.cloud.dto;

import com.sanxin.cloud.entity.GiftHour;
import com.sanxin.cloud.entity.SysRichText;
import lombok.Data;
import java.util.List;

/**
 * 购买时长礼包VO
 */
@Data
public class BuyGiftVO {

    private Integer hour;

    private List<GiftHour> list;

    private RuleTextVo ruleTextVo;

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

    public RuleTextVo getRuleTextVo() {
        return ruleTextVo;
    }

    public void setRuleTextVo(RuleTextVo ruleTextVo) {
        this.ruleTextVo = ruleTextVo;
    }

    @Override
    public String toString() {
        return "BuyGiftVO{" +
                "hour=" + hour +
                ", list=" + list +
                ", ruleTextVo=" + ruleTextVo +
                '}';
    }
}
