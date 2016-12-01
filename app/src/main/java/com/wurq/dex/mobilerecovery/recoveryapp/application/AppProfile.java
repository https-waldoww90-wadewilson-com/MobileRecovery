package com.wurq.dex.mobilerecovery.recoveryapp.application;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;


/**
 * Created by ht-template
 **/
public abstract class AppProfile {
    public static final String AppName = "PoctApplication";

    // URS
    public static final String PRODUCT_NAME = "poct";
    public static final String URS_SERVER_PUBKEY = "30819f300d06092a864886f70d010101050003818d00308189028181009c58533b36458f1e86816a61c8f06bfad70999377d9640b5bebe67f0617dc6177db5f6bbd176767ed0e87c34fbc4cadaeb6a2df057cd890d608247b869d2d067cac0b978361b19ccc6b9dd3466e12e3aabbe32685495ecf15d6a84fc53c67b7a6d29876053e9eee0cb36e6599d5c4c6e242800ce52272fcf6cf54f82389a7ef30203010001";
    public static final String URS_CLIENT_PRIKEY = "30820276020100300d06092a864886f70d0101010500048202603082025c02010002818100a5f934c96bea38d75eba5a41ae98a9ab5856ece059f642d5e0d6a2a68e4f719885c16dccdcd5e60a3caba3d06927cba597d21d3e4e9bc714810318a29eef0e682114f1ffc06009e445effd2b39246cf5d6f4a1ea45429d83213a8f14152bc6136587cfd9b8daa7c7e167244bb7aea371854eb8f82a34dce15bb8d567e7bc8fb90203010001028180501e6050eafb01bd5548f7d0c50b84bbe5c4f04bcca187d9ded33d68d141a0405430ef63f9267809710819e7bd1b2cbf1eee9a973d330d82e4542e4c32148c0b08f5b1d27438b65101de998ea990ecf984bef5a29a721b0a1bba39651dcc1059cf35f2a85012bf7340147644bc38d5fd20fe14f38bdfa571a388843970416e41024100d42f87ec4bfd54b099c4b58a47ffc11d83c6f6f393bf32ba5793c0e214f597221aab0dfa52d5acd8c801d840088b6d87a5fac040180aa3a0db72cde7be191bcd024100c83ed33d316c2bd13d476149541052d00e9e630fae55e53b406863f7dfc4e8e1f415ceac12a587c075dfba1679967cfa6bb7197eacec7ca1d305857e820b8f9d02404e2df123e45fc3db89dc8b9316b22a2248d27f5d01dc19c1c4d0e0e9ac154d0938d03bc736cc19f289c6fc39a3ac53bffe2ce4b6f8e4baa36317eabbef33eb250240283745e1624da13732a1654e7ecd90742c9b6eb5b58396b821a5557e70b87134fffa693971bd40d84c5414617325e00fc31650ce7a183ca79b63de7d9bcc8a99024100927a632f8797e83c680c227af24c6c774a20b06e1d06febe67036b1fe4b5768652ac5b3c7dc9caff813cdbcfeba67823d485bcab7b6aa90eb82291a0624c7912";

    /* package */ static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    public static void setContext(Context context) {
        sContext = context;
    }

    public static final String getPackageName() {
        return sContext.getPackageName();
    }

//    static public boolean isFabricEnabled() {
//        return !ServerEnvs.isDebug();
//    }

    public static void gotoBackground(@NonNull Context context) {
        PackageManager pm = context.getPackageManager();
        ResolveInfo homeInfo = pm.resolveActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME), 0);

        if (homeInfo == null) return;

        ActivityInfo ai = homeInfo.activityInfo;
        Intent startIntent = new Intent(Intent.ACTION_MAIN);
        startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        startIntent.setComponent(new ComponentName(ai.packageName, ai.name));
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(startIntent);
    }

    public static boolean isMainProcess() {
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) sContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : am.getRunningAppProcesses()) {
            if (pid == appProcessInfo.pid) {
                if (TextUtils.equals(sContext.getPackageName(), appProcessInfo.processName)) {
                    Log.i("XXXX", appProcessInfo.processName + "; uid = " + appProcessInfo.uid + "; pid = " + pid);
                    return true;
                }
            }
        }
        return false;
    }
}
