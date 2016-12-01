package com.wurq.dex.mobilerecovery.hearttouch.pay;

/**
 * Created by ht-template
 **/
public class Platforms {
    /**
     * 网易宝支付平台
     */
    public static final String EPAY = "epay";
    /**
     * 支付宝支付平台
     */
    public static final String ALIPAY = "alipay";
    /**
     * 微信支付平台
     */
    public static final String WXPAY = "wxpay";

    /**
     * 支付请求码
     */
    public static final int PAY_REQUEST = 0;
    /**
     * 未支付
     */
    public static final int NO_PAY = 1;
    /**
     * 支付完成
     */
    public static final int OK_PAY = 0;
    /**
     * 网易宝支付接口
     */
    public static final String EPAY_SERVER = "https://epay.163.com/addMobileTradeServlet";
    /**
     * 支付宝sdk结果码
     */
    public static final int SDK_PAY_FLAG = 1;


    /**
     * 支付失败
     */
    public static final int PAY_FAIL = 0;
    /**
     * 取消支付
     */
    public static final int PAY_CANCLE = 1;
    /**
     * 支付成功
     */
    public static final int PAY_SUCCESS = 2;
    /**
     * 支付结果确认中
     */
    public static final int PAY_WAIT = 3;
}
