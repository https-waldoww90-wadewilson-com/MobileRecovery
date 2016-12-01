package com.wurq.dex.mobilerecovery.hearttouch.pay;

/**
 * Created by ht-template
 **/
public class PayCode {
    public static final int SUCCESS_CODE = 200;
    public static final int SDK_CALLED_ERROR = -200;
    public static final int DEFAULT_ERROR_CODE = 400;

    // 支付宝支付“8000”的情况，代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
    // public static final int ALI_PAY_WAITTING_ERROR = 8000;
}
