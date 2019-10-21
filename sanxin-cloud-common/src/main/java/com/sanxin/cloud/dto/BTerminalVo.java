package com.sanxin.cloud.dto;

/**
 * 充电宝实体类
 * @author xiaoky
 * @date 2019-10-14
 */
public class BTerminalVo implements Comparable<BTerminalVo>{
    /**
     * 充电宝Id
     */
    private String terminalId;
    /**
     * 充电宝电量  0~4(20~100电量) 状态为充电中数据才有效
     */
    private Integer level;
    /**
     * 充电槽位——状态为充电中数据才有效
     */
    private String slot;

    /**
     * Gets the value of terminalId.
     *
     * @return the value of terminalId
     */
    public String getTerminalId() {
        return terminalId;
    }

    /**
     * Sets the terminalId.
     *
     * <p>You can use getTerminalId() to get the value of terminalId</p>
     *
     * @param terminalId terminalId
     */
    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    /**
     * Gets the value of level.
     *
     * @return the value of level
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * Sets the level.
     *
     * <p>You can use getLevel() to get the value of level</p>
     *
     * @param level level
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * Gets the value of slot.
     *
     * @return the value of slot
     */
    public String getSlot() {
        return slot;
    }

    /**
     * Sets the slot.
     *
     * <p>You can use getSlot() to get the value of slot</p>
     *
     * @param slot slot
     */
    public void setSlot(String slot) {
        this.slot = slot;
    }

    @Override
    public int compareTo(BTerminalVo o) {
        return o.getLevel().compareTo(this.getLevel());
    }

}
