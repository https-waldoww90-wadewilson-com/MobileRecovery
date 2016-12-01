package com.wurq.dex.mobilerecovery.recoveryapp.application;

import android.app.Application;
import android.content.Context;

import com.wurq.dex.mobilerecovery.BuildConfig;
import com.wurq.dex.mobilerecovery.hearttouch.common.util.ScreenUtil;
import com.wurq.dex.mobilerecovery.hearttouch.common.util.SystemUtil;

/**
 *
 **/
public class RecoveryApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Context context = getApplicationContext();
        ScreenUtil.GetInfo(context);
        AppProfile.setContext(context);

        //崩溃捕获以及日志输出配置
        if (BuildConfig.DEBUG) {
            CrashHandlerL.getInstance().init(context);//debug状态下使用本地崩溃捕获
//            HTLog.installLogTool(getApplicationContext(), getPackageName(), new HTConsoleLogTool("VERBOSE"), true, 5);
//            // add debug for use log filename, remove after debug
//            HTLog.installLogTool(getApplicationContext(), getPackageName(), new HTLogFileLocalProcessLogTool("VERBOSE", //设置当前log的打印级别。支持的打印级别分别为：VERBOSE,DEBUG,INFO,WARN,ERROR,ASSERT。与logcat的级别一一对应。只有等于或高于此级别的log才会被打印出来。
//                    "Android/data/" + getPackageName() + "/cache/htLog/",     //指定把日志打印到SD卡的哪个目录
//                    5), false, 20);
        } else {
            // crash.163.com
//            UserStrategy strategy = new UserStrategy(context);
//            strategy.setChannel(BuildConfig.FLAVOR);
//            strategy.setAnrMonitorStatus(true);//设置是否启用ANR捕获，默认是开启状态，开发者可以根据需要设置关闭ANR捕获
//            CrashHandler.init(context, strategy);
//
//            if (ServerEnvs.isNotOnLine()) { //开发和qa分支的release输出日志文件
//                HTLog.installLogTool(getApplicationContext(), getPackageName(), new HTLogFileLocalProcessLogTool("VERBOSE", //设置当前log的打印级别。支持的打印级别分别为：VERBOSE,DEBUG,INFO,WARN,ERROR,ASSERT。与logcat的级别一一对应。只有等于或高于此级别的log才会被打印出来。
//                        "Android/data/" + getPackageName() + "/cache/htLog/",     //指定把日志打印到SD卡的哪个目录
//                        5), false, 5);
//            } else {
//                HTLog.installLogTool(getApplicationContext(), getPackageName(), new HTConsoleLogTool("VERBOSE"), true, 5);
//            }
        }


//        ShareSDK.initSDK(context);
        // 所有实现IReceivierType的class都可以当做HTEventBus注册的对象
//        HTEventBus.getDefault().registerReceiverClass(ISubscriber.class);

        //必须application做，不能放在启动页，有先进入MainPage的情况
//        HTRefreshRecyclerView.setRefreshViewHolderClass(DotStyleRefreshViewHolder.class);

        // 进入应用就开始在后台请求网络时间
        SystemUtil.updateSystemBias();

        // 初始化支付模块
        // initPay();

        // 初始化皮肤
        // SkinManager.getInstance().initView(this);

        // 字体初始化
        // FontUtil.initView(this);
        // FontUtil.init(this);

        // URS初始化
//        URSdk.createAPI(context, AppProfile.PRODUCT_NAME, AppProfile.URS_SERVER_PUBKEY, AppProfile.URS_CLIENT_PRIKEY);
//        URSdk.addGlobalErrorHandler(new UrsWildcardErrorHandler());
//        URSdk.addGlobalErrorHandler(new UrsRuntimeErrorHandler());

        // HTHttp初始化
//        HTHttp.init();

//        if(ServerEnvs.isDebug()||ServerEnvs.isNotOnLine())  {
////            if (LeakCanary.isInAnalyzerProcess(this)) {
////                // This process is dedicated to LeakCanary for heap analysis.
////                // You should not init your app in this process.
////                return;
////            }
//            refWatcher = LeakCanary.install(this);
//            // Normal app init code..
//        }
        ForegroundCallbacks.get(this);
    }


//    private RefWatcher refWatcher;
//
//    public static RefWatcher getRefWatcher(Context context) {
//        RecoveryApplication application = (RecoveryApplication) context.getApplicationContext();
//        return application.refWatcher;
//    }
    /**
     * 初始化
     */
//    private void initPay() {
//        // 初始化三个支付平台
//
//        Platform wxPlatform = new WxpayPlatform(ResourcesUtil.getString(R.string.weixin_pay), R.drawable.wxpay, "127.0.0.1", WXConstants.APP_ID);
//        Platform neteasePlatform = new EpayPlatform(ResourcesUtil.getString(R.string.netease_pay), R.drawable.epay, "forsta@163.com", "xxxxx", "123456789");
//        Platform aliPlatform = new AlipayPlatform(ResourcesUtil.getString(R.string.ali_pay), R.drawable.alipay);
//
//        PayUtil.addPlatform(PayUtil.PayType.WEI_XIN_PAY, wxPlatform);
//        PayUtil.addPlatform(PayUtil.PayType.NETEASE_PAY, neteasePlatform);
//        PayUtil.addPlatform(PayUtil.PayType.ALI_PAY, aliPlatform);
//
//        Set<Platform> platforms = new HashSet<>();
//        platforms.addAll(CollectionsUtil.newArrayList(wxPlatform, neteasePlatform, aliPlatform));
//
//        String APP_Server = WXConstants.APP_SERVER;
//        String APP_Result = WXConstants.APP_NOTIFY;
//        //默认Dialog
//        IDialog iDialog = new DefaultDialog();
//
//        HTPay.initView(getApplicationContext(), APP_Server, APP_Result, platforms, iDialog);
//    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
