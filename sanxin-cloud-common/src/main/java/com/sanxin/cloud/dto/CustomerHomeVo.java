package com.sanxin.cloud.dto;

import lombok.Data;
import java.util.Date;

@Data
public class CustomerHomeVo {
    private String phone;
    private String email;
    private Integer status;
    private Integer isValid;
    private Date createTime;
    private String token;
    private String headUrl;
    private String nickName;
    private Integer number;
}
