package com.sanxin.cloud.common.alipay;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.sanxin.cloud.common.FunctionUtils;
import com.sanxin.cloud.config.redis.RedisUtils;
import com.sanxin.cloud.common.alipay.config.AlipayConfig;
import com.sanxin.cloud.common.alipay.util.AlipaySubmit;
import com.sanxin.cloud.entity.InfoAli;
import com.sanxin.cloud.exception.ThrowJsonException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付宝支付
 *
 * @author Arno
 */
public class AlipayUtils {

    public static Map<String, String> getReturnMap(HttpServletRequest request) {
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        return params;
    }

    /**
     * 支付宝APP支付
     *
     * @param payCode     交易单号 每次都不能一样
     * @param orderPrice 订单金额
     * @param noticeUrl  异步回调地址
     * @return
     */
    public static Map<String, Object> getAliPayApp(String payCode, String orderPrice, String noticeUrl) {
        InfoAli aliInfo = RedisUtils.getInstance().getAliInfo();
        Map<String, Object> map = new HashMap<String, Object>();
        //组装 app 支付去支付签名数据
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("partner", aliInfo.getPartner());
        //商户账号
        sParaTemp.put("seller_id", aliInfo.getPartnerAccount());
        //支付单号
        sParaTemp.put("out_trade_no", payCode);
        //订单名称
        sParaTemp.put("subject", aliInfo.getSubject());
        //订单描述
        sParaTemp.put("body", aliInfo.getBody());
        //付款金额
        sParaTemp.put("total_fee", orderPrice);
        sParaTemp.put("notify_url", noticeUrl);
        sParaTemp.put("service", AlipayConfig.APP_SERVICE);
        //支付类型 必填，不能修改
        sParaTemp.put("payment_type", AlipayConfig.PAYMENT_TYPE);
        sParaTemp.put("_input_charset", AlipayConfig.INPUT_CHARSET);
        String str = AlipaySubmit.buildRequestPara_app(sParaTemp, aliInfo);
        map.put("info", str);
        return map;
    }

    /**
     * 支付宝web端支付数据拼接
     *
     * @param payCode    交易单号
     * @param money      金额
     * @param notifyUrl 异步地址
     * @param returnUrl 同步地址
     * @return
     */
    public static Map<String, String> getZhiFuInfo(String payCode, String money, String notifyUrl, String returnUrl) {
        InfoAli info = RedisUtils.getInstance().getAliInfo();
        Map<String, String> sParaTemp = new HashMap<String, String>();
        try {
            sParaTemp.put("service", AlipayConfig.WEB_SERVICE);
            sParaTemp.put("partner", info.getPartner());
            sParaTemp.put("seller_id", info.getPartner());
            sParaTemp.put("_input_charset", AlipayConfig.INPUT_CHARSET);
            sParaTemp.put("payment_type", AlipayConfig.PAYMENT_TYPE);
            sParaTemp.put("notify_url", notifyUrl);
            sParaTemp.put("return_url", returnUrl);
            sParaTemp.put("out_trade_no", payCode);
            sParaTemp.put("subject", info.getSubject());
            sParaTemp.put("total_fee", money);
            sParaTemp.put("body", info.getBody());
        } catch (Exception e) {
            throw new ThrowJsonException("签名失败");
        }
        //其他业务参数根据在线开发文档，添加参数.
        //文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.O9yorI&treeId=62&articleId=103740&docType=1
        //如sParaTemp.put("参数名","参数值");
        return sParaTemp;
    }

    /**
     * 支付宝手机端网页  现在都用rsa2 签名退款
     *
     * @param payCode
     * @param money
     * @param notifyUrl
     * @param returnUrl
     */
    public static String ali_mobile_pay(String payCode, String money, String notifyUrl, String returnUrl) {
        InfoAli info = RedisUtils.getInstance().getAliInfo();
        // 销售产品码 必填 固定产品编码 支付宝提供的
        String form = "";
        try {
            String product_code = "QUICK_WAP_WAY";
            // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
            String url = "https://openapi.alipay.com/gateway.do";
            //调用RSA签名方式
            AlipayClient client = new DefaultAlipayClient(url, info.getRefundApp(), info.getRefundPrivateKey(),
                    AlipayConfig.FORMAT, AlipayConfig.INPUT_CHARSET, info.getRefundPublicKey(), AlipayConfig.REFUND_SIGN_TYPE);
            AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();

            // 封装请求支付信息
            AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
            model.setOutTradeNo(payCode);
            model.setSubject(info.getSubject());
            model.setTotalAmount(money);
            model.setBody(info.getBody());
            model.setProductCode(product_code);
            alipayRequest.setBizModel(model);
            // 设置异步通知地址
            alipayRequest.setNotifyUrl(notifyUrl);
            // 设置同步地址
            alipayRequest.setReturnUrl(returnUrl);
            // form表单生产
            form = client.pageExecute(alipayRequest).getBody();
        } catch (Exception e) {
            throw new ThrowJsonException("签名失败");
        }
        return form;
    }

    public static String ali_program_pay(String payCode, String money, String notifyUrl, String returnUrl) {
        InfoAli info = RedisUtils.getInstance().getAliInfo();
        // 销售产品码 必填 固定产品编码 支付宝提供的
        String form = "";
        try {
            String product_code = "QUICK_MSECURITY_PAY";
            // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
            String url = "https://openapi.alipay.com/gateway.do";
            //调用RSA签名方式
            AlipayClient client = new DefaultAlipayClient(url, info.getRefundApp(), info.getRefundPrivateKey(),
                    AlipayConfig.FORMAT, AlipayConfig.INPUT_CHARSET, info.getRefundPublicKey(), AlipayConfig.REFUND_SIGN_TYPE);
            AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();

            // 封装请求支付信息
            AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
            model.setOutTradeNo(payCode);
            model.setSubject(info.getSubject());
            model.setTotalAmount(money);
            model.setBody(info.getBody());
            model.setProductCode(product_code);
            alipayRequest.setBizModel(model);
            // 设置异步通知地址
            alipayRequest.setNotifyUrl(notifyUrl);
            // 设置同步地址
            alipayRequest.setReturnUrl(returnUrl);
            // form表单生产
            form = client.pageExecute(alipayRequest).getBody();
        } catch (Exception e) {
            throw new ThrowJsonException("签名失败");
        }
        return form;
    }

    /**
     * web退款
     *
     * @param payCode
     * @param tranCode
     * @param money
     * @return
     */
    public static String aliWebRefund(InfoAli info, String payCode, String tranCode, String money) {
        // 发送请求
        String strResponse = "";
        try {
            String privateKey = info.getRefundPrivateKey();
            String publicKey = info.getRefundPublicKey();
            String webAppid = info.getRefundWeb();
            AlipayClient alipayClient = new DefaultAlipayClient
                    ("https://openapi.alipay.com/gateway.do", webAppid,
                            privateKey, "json", AlipayConfig.INPUT_CHARSET, publicKey, AlipayConfig.REFUND_SIGN_TYPE);
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
            String outRequestNo = FunctionUtils.getOrderCode("R");
            JSONObject biz = new JSONObject();
            biz.put("out_trade_no", payCode);
            biz.put("refund_amount", money);
            biz.put("trade_no", tranCode);
            biz.put("out_request_no", outRequestNo);
            request.setBizContent(JSONObject.toJSONString(biz));
            AlipayTradeRefundResponse response = alipayClient.execute(request);
            if (!"10000".equals(response.getCode())) {
                strResponse = response.getSubMsg();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ThrowJsonException("退款失败");
        }
        return strResponse;
    }

    /**
     * app退款
     *
     * @param payCode
     * @param tranCode
     * @param money
     * @return
     */
    public static String aliAppRefund(InfoAli info, String payCode, String tranCode, String money) {
        // 发送请求
        String strResponse = "";
        try {
            String privateKey = info.getRefundPrivateKey();
            String publicKey = info.getRefundPublicKey();
            String appAppid = info.getRefundApp();
            AlipayClient alipayClient = new DefaultAlipayClient
                    ("https://openapi.alipay.com/gateway.do", appAppid,
                            privateKey, "json", AlipayConfig.INPUT_CHARSET, publicKey, AlipayConfig.REFUND_SIGN_TYPE);
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
            String outRequestNo = FunctionUtils.getOrderCode("R");
            JSONObject biz = new JSONObject();
            biz.put("out_trade_no", payCode);
            biz.put("refund_amount", money);
            biz.put("trade_no", tranCode);
            biz.put("out_request_no", outRequestNo);
            request.setBizContent(JSONObject.toJSONString(biz));
            AlipayTradeRefundResponse response = alipayClient.execute(request);
            if (!"10000".equals(response.getCode())) {
                strResponse = response.getSubMsg();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ThrowJsonException("退款失败");
        }
        return strResponse;
    }
}
