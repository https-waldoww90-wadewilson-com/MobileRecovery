package com.wurq.dex.mobilerecovery.hearttouch.common.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

//import com.wurq.dex.mobilerecovery.htlog.HTLog;
import com.wurq.dex.mobilerecovery.recoveryapp.application.AppProfile;
import com.wurq.dex.mobilerecovery.recoveryapp.application.GlobalInfo;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by ht-template
 **/
public class DeviceUtil {
    public static String getLocalMacAddress() {
        WifiManager wifi = (WifiManager) AppProfile.getContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
//            HTLog.e("WifiPreference IpAddress: " + ex.toString());
        }
        return null;
    }


    public static String getPhoneType() {
        return android.os.Build.MODEL;
    }

    public static String getSystemName() {
        //return System.getProperty("os.name");
        return "android";
    }

    public static String getSytemVersion() {
        return android.os.Build.VERSION.SDK;
    }

    public static int getResolution() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) AppProfile.getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);

        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;

        return widthPixels * heightPixels;
    }

    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static boolean isWifiConnected() {
        Context context = AppProfile.getContext();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiNetworkInfo.isConnected();
    }

    /**
     * 获取手机IMEI号+mac地址, 因为山寨机可能没有imei
     *
     * @return
     */
    public static String getPhoneUUID() {
        try {
            String uuid = GlobalInfo.getPhoneUUID();
            if (!TextUtils.isEmpty(uuid)) {
                return uuid;
            }
        } catch (Exception e) {

        }
        Context context = AppProfile.getContext();
        TelephonyManager mTelephonyMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String mac = info.getMacAddress();
        String uuid = mTelephonyMgr.getDeviceId() + "@" + info.getMacAddress();
        if (!TextUtils.isEmpty(mac)) {
            GlobalInfo.setPhoneUUID(uuid);
        }
        return uuid;
    }
}
