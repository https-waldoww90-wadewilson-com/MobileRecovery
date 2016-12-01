package com.wurq.dex.mobilerecovery.recoveryapp.attentionmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by wurongqiu on 16/7/31.
 * for mobilephone reboot
 */
public class BootAlarmReceiver extends BroadcastReceiver {
//    AlarmReceiver alarm = new AlarmReceiver();
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            AttentionManager.getInstance(context).postAlarm();
            //
//            alarm.testAlarm(context);
        }
    }
}