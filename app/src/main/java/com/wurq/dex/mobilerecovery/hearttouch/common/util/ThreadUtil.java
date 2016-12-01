package com.wurq.dex.mobilerecovery.hearttouch.common.util;

import android.support.annotation.NonNull;

/**
 * Created by ht-template
 **/
public class ThreadUtil {
    public static void runOnAnsy(@NonNull Runnable runnable,
                                 @NonNull String threadName) {
        Thread thread = new Thread(runnable, threadName);
        thread.run();
    }
}
