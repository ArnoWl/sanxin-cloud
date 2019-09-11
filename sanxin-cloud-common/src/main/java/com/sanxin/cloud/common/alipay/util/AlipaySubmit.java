package com.sanxin.cloud.common.alipay.util;

import com.sanxin.cloud.common.alipay.config.AlipayConfig;
import com.sanxin.cloud.common.alipay.sign.MD5;
import com.sanxin.cloud.common.alipay.sign.RSA;
import com.sanxin.cloud.common.alipay.util.httpClient.HttpProtocolHandler;
import com.sanxin.cloud.common.alipay.util.httpClient.HttpRequest;
import com.sanxin.cloud.common.alipay.util.httpClient.HttpResponse;
import com.sanxin.cloud.common.alipay.util.httpClient.HttpResultType;
import com.sanxin.cloud.entity.InfoAli;
import org.apache.commons.httpclient.NameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* *
 *类名：AlipaySubmit
 *功能：支付宝各接口请求提交类
 *详细：构造支付宝各接口表单HTML文本，获取远程HTTP数据
 *版本：3.3
 *日期：2012-08-13
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipaySubmit {

    /**
     * 支付宝提供给商户的服务接入网关URL(新)
     */
    public static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";


    /**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
	public static String buildRequestMysign(Map<String, String> sPara, InfoAli aliInfo) {
    	String prestr = AlipayCore.createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        if(AlipayConfig.SIGN_TYPE.equals("RSA") ) {
        	mysign = RSA.sign(prestr,aliInfo.getAliPrivateKey(), AlipayConfig.INPUT_CHARSET);
        }
        if(AlipayConfig.SIGN_TYPE.equals("MD5") ) {
        	mysign = MD5.sign(prestr,aliInfo.getPrivateKey(), AlipayConfig.INPUT_CHARSET);
        }
        return mysign;
    }

	 /**
     * 生成APP签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
	public static String buildRequestMysign_app(Map<String, String> sPara,InfoAli aliInfo) {
		String prestr = AlipayCore.createLinkString_app(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        if(AlipayConfig.SIGN_TYPE.equals("RSA") ) {
        		mysign = RSA.sign(prestr, aliInfo.getAliPrivateKey(), AlipayConfig.INPUT_CHARSET);
        }
        return mysign;
    }
    /**
     * 生成要请求给支付宝的参数数组
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
    private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp,InfoAli aliInfo) {
        //除去数组中的空值和签名参数
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
        //生成签名结果
        String mysign = buildRequestMysign(sPara,aliInfo);
        //签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
        sPara.put("sign_type", AlipayConfig.SIGN_TYPE);

        return sPara;
    }

    /**
     * 获取app  前面链接 返回给 app  生成app调用支付宝的全签名字符串
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
    public static String buildRequestPara_app(Map<String, String> sParaTemp,InfoAli aliInfo) {
        //除去数组中的空值和签名参数
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
        //生成签名结果
        String mysign = buildRequestMysign_app(sPara,aliInfo);
        String prestr1="";
        for (String key : sPara.keySet()) {
            String value = sPara.get(key);
            prestr1+=key+"=\""+value.toString()+"\"&";
        }
        //最终app调用的签名内容
        String str=prestr1+"sign=\""+URLEncoder.encode(mysign)+"\"&sign_type=\""+AlipayConfig.SIGN_TYPE+"\"";
        return str;
    }

    /**
     * 建立请求，以表单HTML形式构造（默认）
     * @param sParaTemp 请求参数数组
     * @param strMethod 提交方式。两个值可选：post、get
     * @param strButtonName 确认按钮显示文字
     * @return 提交表单HTML文本
     */
    public static String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName,InfoAli aliInfo) {
        //待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp,aliInfo);
        List<String> keys = new ArrayList<String>(sPara.keySet());

        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + ALIPAY_GATEWAY_NEW
                      + "_input_charset=" + AlipayConfig.INPUT_CHARSET + "\" method=\"" + strMethod
                      + "\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }

        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");
        return sbHtml.toString();
    }

    /**
     * 建立请求，以表单HTML形式构造，带文件上传功能
     * @param sParaTemp 请求参数数组
     * @param strMethod 提交方式。两个值可选：post、get
     * @param strButtonName 确认按钮显示文字
     * @param strParaFileName 文件上传的参数名
     * @return 提交表单HTML文本
     */
    public static String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName, String strParaFileName,InfoAli aliInfo) {
        //待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp,aliInfo);
        List<String> keys = new ArrayList<String>(sPara.keySet());

        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\"  enctype=\"multipart/form-data\" action=\"" + ALIPAY_GATEWAY_NEW
                      + "_input_charset=" + AlipayConfig.INPUT_CHARSET + "\" method=\"" + strMethod
                      + "\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }

        sbHtml.append("<input type=\"file\" name=\"" + strParaFileName + "\" />");

        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");

        return sbHtml.toString();
    }

    /**
     * 建立请求，以模拟远程HTTP的POST请求方式构造并获取支付宝的处理结果
     * 如果接口中没有上传文件参数，那么strParaFileName与strFilePath设置为空值
     * 如：buildRequest("", "",sParaTemp)
     * @param strParaFileName 文件类型的参数名
     * @param strFilePath 文件路径
     * @param sParaTemp 请求参数数组
     * @return 支付宝处理结果
     * @throws Exception
     */
    public static String buildRequest(String strParaFileName, String strFilePath,Map<String, String> sParaTemp,InfoAli aliInfo) throws Exception {
        //待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp,aliInfo);

        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        //设置编码集
        request.setCharset(AlipayConfig.INPUT_CHARSET);

        request.setParameters(generatNameValuePair(sPara));
        request.setUrl(ALIPAY_GATEWAY_NEW+"_input_charset="+AlipayConfig.INPUT_CHARSET);

        HttpResponse response = httpProtocolHandler.execute(request,strParaFileName,strFilePath);
        if (response == null) {
            return null;
        }

        String strResult = response.getStringResult();

        return strResult;
    }

    /**
     * MAP类型数组转换成NameValuePair类型
     * @param properties  MAP类型数组
     * @return NameValuePair类型数组
     */
    private static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }

        return nameValuePair;
    }

    /**
     * 用于防钓鱼，调用接口query_timestamp来获取时间戳的处理函数
     * 注意：远程解析XML出错，与服务器是否支持SSL等配置有关
     * @return 时间戳字符串
     * @throws IOException
     * @throws DocumentException
     * @throws MalformedURLException
     */
	public static String queryTimestamp(InfoAli aliInfo) throws MalformedURLException,
                                                        DocumentException, IOException {

        //构造访问query_timestamp接口的URL串
        String strUrl = ALIPAY_GATEWAY_NEW + "service=query_timestamp&partner=" + aliInfo.getPartner() + "&_input_charset" +AlipayConfig.INPUT_CHARSET;
        StringBuffer result = new StringBuffer();

        SAXReader reader = new SAXReader();
        Document doc = reader.read(new URL(strUrl).openStream());

        List<Node> nodeList = doc.selectNodes("//alipay/*");

        for (Node node : nodeList) {
            // 截取部分不需要解析的信息
            if (node.getName().equals("is_success") && node.getText().equals("T")) {
                // 判断是否有成功标示
                List<Node> nodeList1 = doc.selectNodes("//response/timestamp/*");
                for (Node node1 : nodeList1) {
                    result.append(node1.getText());
                }
            }
        }

        return result.toString();
    }

	/**
	 * 支付宝web端支付数据拼接
	 * @param request
	 * @param payOrderCode
	 * @param notify_url
	 * @param return_url
	 * @param Money
	 * @param body
	 * @return
	 */
	public static Map<String,String> getZhiFuInfo(HttpServletRequest request,InfoAli aliInfo,String payOrderCode, String notify_url,String return_url,
			String Money, String body,String subject){
		Map<String, String> sParaTemp = new HashMap<String, String>();
		try {
			sParaTemp.put("service", AlipayConfig.WEB_SERVICE);
	        sParaTemp.put("partner", aliInfo.getPartner());
	        sParaTemp.put("seller_id", aliInfo.getPartner());
	        sParaTemp.put("_input_charset", AlipayConfig.INPUT_CHARSET);
			sParaTemp.put("payment_type", AlipayConfig.PAYMENT_TYPE);
			sParaTemp.put("notify_url", notify_url);
			sParaTemp.put("return_url", return_url);
			sParaTemp.put("out_trade_no", payOrderCode);
			sParaTemp.put("subject",subject );
			sParaTemp.put("total_fee", Money);
			sParaTemp.put("body", body);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//其他业务参数根据在线开发文档，添加参数.
        //文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.O9yorI&treeId=62&articleId=103740&docType=1
        //如sParaTemp.put("参数名","参数值");
		return sParaTemp;
	}
}
