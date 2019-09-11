package com.sanxin.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 支付宝配置表
 * </p>
 *
 * @author xiaoky
 * @since 2019-09-09
 */
public class InfoAli implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ali_id", type = IdType.AUTO)
    private Integer aliId;

    private String aliName;

    /**
     * partner
     */
    private String partner;

    /**
     * 商户的私钥
     */
    private String privateKey;

    /**
     * 商户号
     */
    private String partnerAccount;

    /**
     * 支付宝的公钥 无需修改 一定确定不需要修改
     */
    private String aliPublicKey;

    /**
     * app支付私钥
     */
    private String aliPrivateKey;

    /**
     * 订单描述
     */
    private String body;

    /**
     * 订单名称
     */
    private String subject;

    /**
     * 证书位置
     */
    private String cert;

    /**
     * 回调域名
     */
    private String returnUrl;

    /**
     * web退款编号
     */
    private String refundWeb;

    /**
     * app退款编号
     */
    private String refundApp;

    /**
     * 退款公钥
     */
    private String refundPublicKey;

    /**
     * 退款私钥
     */
    private String refundPrivateKey;

    public Integer getAliId() {
        return aliId;
    }

    public void setAliId(Integer aliId) {
        this.aliId = aliId;
    }
    public String getAliName() {
        return aliName;
    }

    public void setAliName(String aliName) {
        this.aliName = aliName;
    }
    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }
    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
    public String getPartnerAccount() {
        return partnerAccount;
    }

    public void setPartnerAccount(String partnerAccount) {
        this.partnerAccount = partnerAccount;
    }
    public String getAliPublicKey() {
        return aliPublicKey;
    }

    public void setAliPublicKey(String aliPublicKey) {
        this.aliPublicKey = aliPublicKey;
    }
    public String getAliPrivateKey() {
        return aliPrivateKey;
    }

    public void setAliPrivateKey(String aliPrivateKey) {
        this.aliPrivateKey = aliPrivateKey;
    }
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }
    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
    public String getRefundWeb() {
        return refundWeb;
    }

    public void setRefundWeb(String refundWeb) {
        this.refundWeb = refundWeb;
    }
    public String getRefundApp() {
        return refundApp;
    }

    public void setRefundApp(String refundApp) {
        this.refundApp = refundApp;
    }
    public String getRefundPublicKey() {
        return refundPublicKey;
    }

    public void setRefundPublicKey(String refundPublicKey) {
        this.refundPublicKey = refundPublicKey;
    }
    public String getRefundPrivateKey() {
        return refundPrivateKey;
    }

    public void setRefundPrivateKey(String refundPrivateKey) {
        this.refundPrivateKey = refundPrivateKey;
    }

    @Override
    public String toString() {
        return "InfoAli{" +
        "aliId=" + aliId +
        ", aliName=" + aliName +
        ", partner=" + partner +
        ", privateKey=" + privateKey +
        ", partnerAccount=" + partnerAccount +
        ", aliPublicKey=" + aliPublicKey +
        ", aliPrivateKey=" + aliPrivateKey +
        ", body=" + body +
        ", subject=" + subject +
        ", cert=" + cert +
        ", returnUrl=" + returnUrl +
        ", refundWeb=" + refundWeb +
        ", refundApp=" + refundApp +
        ", refundPublicKey=" + refundPublicKey +
        ", refundPrivateKey=" + refundPrivateKey +
        "}";
    }
}
