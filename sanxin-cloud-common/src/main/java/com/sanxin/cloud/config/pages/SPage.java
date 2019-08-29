package com.sanxin.cloud.config.pages;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author arno
 * @version 1.0
 * @date 2019-08-29
 */
public class SPage<T> extends Page<T> {

    /**
     * 当前页
     */
    private Integer page=1;

    /**
     * 每页显示条数
     */
    private Integer limit=10;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.setCurrent(page);
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.setSize(limit);
        this.limit = limit;
    }
}
