package cn.com.zhihetech.online.commons;

import cn.com.zhihetech.online.bean.SerializableAndCloneable;

/**
 * Created by YangDaiChun on 2016/1/4.
 */
public class PingPPConfig extends SerializableAndCloneable {
    /**
     * pingpp 管理平台对应的 API key
     */
    public static final String API_KEY = "sk_test_nn9eb9fHqT08COmzz5q5efXL";

    /**
     * pingpp 管理平台对应的应用 ID
     */
    public static final String APP_ID = "app_z5mT8OCOuXXTinPy";

    private static final String PAY_ALIPAY = "alipay";
    private static final String PAY_WXPAY = "wx";

    public static final int PAY_NULL_CODE = 0 ;   //未选择支付方式
    public static  final int PAY_ALIPAY_CODE = 1;   //支付宝支付
    public static final int PAY_WXPAY_CODE = 2;   //微信支付

    public static String getChannel(int i){
        if(i == PAY_ALIPAY_CODE){
            return PAY_ALIPAY;
        }else if(i == PAY_WXPAY_CODE){
            return PAY_WXPAY;
        }else {
            return null;
        }
    }
}
