package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 定时任务时间表
 * </p>
 *
 * @author xiaoky
 * @since 2019-10-24
 */
public class InfoAuto implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 自动取消订单（单位分钟）
     */
    private Integer autoCancelOrder;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getAutoCancelOrder() {
        return autoCancelOrder;
    }

    public void setAutoCancelOrder(Integer autoCancelOrder) {
        this.autoCancelOrder = autoCancelOrder;
    }

    @Override
    public String toString() {
        return "InfoAuto{" +
        "id=" + id +
        ", autoCancelOrder=" + autoCancelOrder +
        "}";
    }
}
