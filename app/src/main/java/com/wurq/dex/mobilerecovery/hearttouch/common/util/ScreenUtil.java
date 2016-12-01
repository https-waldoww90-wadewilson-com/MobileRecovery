package com.wurq.dex.mobilerecovery.hearttouch.common.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.wurq.dex.mobilerecovery.recoveryapp.application.AppProfile;

import java.lang.reflect.Field;

/**
 * Created by ht-template
 **/
public class ScreenUtil {
    private static final String TAG = AppProfile.AppName + ".ScreenUtil";
    public static int screenWidth;
    public static int screenHeight;
    public static int screenMin;// 宽高中，较小的值
    public static int screenMax;// 宽高中，较大的值
    public static float density;
    public static float scaleDensity;
    public static float xdpi;
    public static float ydpi;
    public static int densityDpi;
    public static int dialogWidth;
    public static int statusbarheight;
    public static int navbarheight;
    static int sbarHeight = 0;
    private static double RATIO = 0.85;

    public static void GetInfo(Context context) {
        if (null == context) {
            return;
        }
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        screenMin = (screenWidth > screenHeight) ? screenHeight : screenWidth;
        screenMax = (screenWidth < screenHeight) ? screenHeight : screenWidth;
        density = dm.density;
        scaleDensity = dm.scaledDensity;
        xdpi = dm.xdpi;
        ydpi = dm.ydpi;
        densityDpi = dm.densityDpi;
        statusbarheight = getStatusBarHeight(context);
        navbarheight = getNavBarHeight(context);
//        Log.d(TAG, "screenWidth=" + screenWidth + " screenHeight=" + screenHeight + " density=" + density);
    }

    public static int getStatusBarHeight() {
        return getStatusBarHeight(AppProfile.getContext());
    }

    public static int getStatusBarHeight(Context context) {
        if (sbarHeight > 0) return sbarHeight;

        Class<?> c = null;
        Object obj = null;
        Field field = null;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            sbarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception E) {
            E.printStackTrace();
        }
        return sbarHeight;
    }

    public static int getNavBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static int dip2px(float dipValue) {
        final float scale = ScreenUtil.getDisplayDensity();
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(float pxValue) {
        final float scale = ScreenUtil.getDisplayDensity();
        return (int) (pxValue / scale + 0.5f);
    }

    public static float px2dp(int pxValue) {
        return pxValue / ScreenUtil.getDisplayDensity();
    }

    private static float getDisplayDensity() {
        if (density == 0) {
            GetInfo(AppProfile.getContext());
        }
        return density;
    }

    public static int getDisplayWidth() {
        if (screenWidth == 0) {
            GetInfo(AppProfile.getContext());
        }
        return screenWidth;
    }

    public static int getDisplayHeight() {
        if (screenHeight == 0) {
            GetInfo(AppProfile.getContext());
        }
        return screenHeight;
    }

    public static int getScreenMin() {
        if (screenMin == 0) {
            GetInfo(AppProfile.getContext());
        }
        return screenMin;
    }

    public static int getScreenMax() {
        if (screenMin == 0) {
            GetInfo(AppProfile.getContext());
        }
        return screenMax;
    }

    public static int getDialogWidth() {
        dialogWidth = (int) (getScreenMin() * RATIO);
        return dialogWidth;
    }
}
