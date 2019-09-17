package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 充电宝信息表
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-17
 */
public class BDeviceTerminal implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 充电宝Id
     */
    private String terminalId;

    /**
     * 1 充电中  2 已借出  3 已销毁(卖了)
     */
    private Integer status;

    /**
     * 充电宝电量  0~4(20~100电量) 状态为充电中数据才有效
     */
    private Integer level;

    /**
     * 柜机编号——状态为充电中数据才有效
     */
    private String dCode;

    /**
     * 用户id——状态为已销毁数据才有效
     */
    private Integer cid;

    /**
     * 充电宝借出次数
     */
    private Integer lendNum;

    /**
     * 最后借出时间
     */
    private Date lastLendTime;

    /**
     * 最后归还时间
     */
    private Date lastRevertTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
    public String getdCode() {
        return dCode;
    }

    public void setdCode(String dCode) {
        this.dCode = dCode;
    }
    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }
    public Integer getLendNum() {
        return lendNum;
    }

    public void setLendNum(Integer lendNum) {
        this.lendNum = lendNum;
    }
    public Date getLastLendTime() {
        return lastLendTime;
    }

    public void setLastLendTime(Date lastLendTime) {
        this.lastLendTime = lastLendTime;
    }
    public Date getLastRevertTime() {
        return lastRevertTime;
    }

    public void setLastRevertTime(Date lastRevertTime) {
        this.lastRevertTime = lastRevertTime;
    }

    @Override
    public String toString() {
        return "BDeviceTerminal{" +
        "id=" + id +
        ", terminalId=" + terminalId +
        ", status=" + status +
        ", level=" + level +
        ", dCode=" + dCode +
        ", cid=" + cid +
        ", lendNum=" + lendNum +
        ", lastLendTime=" + lastLendTime +
        ", lastRevertTime=" + lastRevertTime +
        "}";
    }
}
