package com.sanxin.cloud.app.api.common;

/**
 * @author Janice
 * @date 2019-08-06
 */
public class MappingUtils {

    /**登录*/
    public static final String REGISTER="/register";

    /**登录*/
    public static final String LOGIN="/login";

    /** 发送忘记密码验证码 */
    public static final String SEND_FORGET_CODE = "/sendForgetCode";

    /** 找回密码操作 */
    public static final String FORGET_PASSWORD = "/forgetPassword";

    /** 个人资料 */
    public static final String PERSONAL_INFORM = "/personalInform";

    /** 修改个人资料 */
    public static final String UPDATE_PERSONAL_INFORM = "/updatePersonalInform";

    /** 修改登录/支付密码 */
    public static final String UPDATE_PASSWORD = "/updatePassword";

    /**注册发送验证码*/
    public static final String SEND_REGISTER_CODE="/sendRegisterCode";

    /**注册*/
    public static final String HANDLE_REGISTER="/handleRegister";

    /**根据经纬度分页搜索周边商铺*/
    public static final String NRARBY_BUSINESS="/nearbyBusiness";

    /**根据经纬度和范围搜索周边商铺*/
    public static final String RANGE_SHOP="/rangeShop";

}
