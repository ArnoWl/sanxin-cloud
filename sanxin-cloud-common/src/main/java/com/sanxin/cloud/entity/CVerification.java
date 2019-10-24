package com.sanxin.cloud.entity;

import java.io.Serializable;

/**
 * <p>
 * 用户第三方登录token
 * </p>
 *
 * @author Arno
 * @since 2019-10-23
 */
public class CVerification implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Integer cid;

    /**
     * googleId
     */
    private String googleId;

    /**
     * facebookId
     */
    private String facebookId;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }
    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }
    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    @Override
    public String toString() {
        return "CVerification{" +
        "cid=" + cid +
        ", googleId=" + googleId +
        ", facebookId=" + facebookId +
        "}";
    }
}
