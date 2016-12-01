package com.wurq.dex.mobilerecovery.recoveryapp.attentionmanager;

/**
 * Created by wurongqiu on 16/7/31.
 */

public final class CommonConstants {

    public CommonConstants() {
        // don't allow the class to be instantiated
    }

//    public static final int SNOOZE_DURATION = 20000;
    public static final int DEFAULT_TIMER_DURATION = 10000;
    public static final String ACTION_SNOOZE = "com.netease.poctapp.attentionmanager.AlarmService.ACTION_SNOOZE";
    public static final String ACTION_DISMISS = "com.netease.poctapp.attentionmanager.AlarmService.ACTION_DISMISS";
    public static final String ACTION_PING = "com.netease.poctapp.attentionmanager.AlarmService.ACTION_PING";
    public static final String EXTRA_MESSAGE= "com.netease.poctapp.attentionmanager.AlarmService.EXTRA_MESSAGE";
    public static final String EXTRA_TIMER = "com.netease.poctapp.attentionmanager.AlarmService.EXTRA_TIMER";
    public static final int NOTIFICATION_ID = 001;
    public static final String DEBUG_TAG = "AlarmService.CommonConstants";
}
