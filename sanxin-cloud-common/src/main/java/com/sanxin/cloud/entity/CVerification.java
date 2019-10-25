package com.sanxin.cloud.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户第三方登录token
 * </p>
 *
 * @author Arno
 * @since 2019-10-23
 */
@Data
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
}
