package com.sanxin.cloud.dto;

import com.alibaba.fastjson.JSONArray;

/**
 *
 * @author xiaoky
 * @date 2019-10-17
 */
public class RuleTextVo {
    private String title;
    private JSONArray content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public JSONArray getContent() {
        return content;
    }

    public void setContent(JSONArray content) {
        this.content = content;
    }
}
