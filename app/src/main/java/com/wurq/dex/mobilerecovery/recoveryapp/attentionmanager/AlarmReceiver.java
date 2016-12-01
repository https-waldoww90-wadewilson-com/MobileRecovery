package com.wurq.dex.mobilerecovery.recoveryapp.attentionmanager;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

//import com.wurq.dex.mobilerecovery.recoveryapp.config.Log;

/**
 * Created by wurongqiu on 16/7/25.
 */
public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";
    private AlarmManager alarmManager;
    private PendingIntent sender;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG,"onReceive entering...");
        boolean isForground = isApplicationBroughtToBackground(context);
        Log.d(TAG,"onReceive isForground = "+isForground);
        if(false == isForground)
        {
            Log.d(TAG,"onReceive false...");
//            Toast.makeText(context, "闹铃响了~~", Toast.LENGTH_LONG).show();
        }
        else {
            Log.d(TAG,"onReceive true...");
            //
            Intent service = new Intent(context, AlarmService.class);
        /*
         * example
         * String message = "闹铃响了~~";
         *
         * intent.putExtra(CommonConstants.EXTRA_MESSAGE, message);
         * intent.setAction(CommonConstants.ACTION_PING);
         */
            String message = "闹铃响了~~";
            intent.putExtra(CommonConstants.EXTRA_MESSAGE, message);
            context.startService(service);
        }
    }

    /*
     * 判断当前应用程序处于前台还是后台
     * @param context
     * @return
     */
    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public void postAlarm(Context context)
    {
        Intent intent = new Intent(context, AlarmReceiver.class);
         sender = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 5);

        alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

        //  boot event
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
//                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, sender);

//        setBootAlarm(context);
    }



    // attention time clock
    private void timeAlarm(Context context, int minute, int hour)
    {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);

        long firstTime = SystemClock.elapsedRealtime();
        long systemTime = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        // +8， adjust minute, hours, day
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        //
        long selectTime = calendar.getTimeInMillis();

        // from 2day
        if(systemTime > selectTime) {
            Toast.makeText(context,"设置的时间小于当前时间", Toast.LENGTH_SHORT).show();
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            selectTime = calendar.getTimeInMillis();
        }

        long time = selectTime - systemTime;
        firstTime += time;

        AlarmManager manager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,firstTime, 333, sender);  // 间隔时间
//        Log.i(TAG,"time ==== " + time +", selectTime ===== "
//                + selectTime + ", systemTime ==== " + systemTime +", firstTime === " + firstTime);
        Toast.makeText(context,"sucsess ", Toast.LENGTH_LONG).show();

        // TODO
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
//                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, sender);
//        setBootAlarm(context);
    }


    // BEGIN_INCLUDE(cancel_alarm)
    public void cancelAlarm(Context context) {
        // If the alarm has been set, cancel it.
        if (alarmManager!= null) {
            alarmManager.cancel(sender);
        }

        // Disable {@code SampleBootReceiver} so that it doesn't automatically restart the
        // alarm when the device is rebooted.
        ComponentName receiver = new ComponentName(context, BootAlarmReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }


    /*
    * set boot alarm
    */
    private void setBootAlarm(Context context){
        // Enable {@code BootAlarmReceiver} to automatically restart the alarm when the
        // device is rebooted.
        ComponentName receiver = new ComponentName(context, BootAlarmReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    /*
     * cancel restart boot feature
     */
    public void cancelBootAlarm(Context context) {

        // Disable {@code BootAlarmReceiver} so that it doesn't automatically restart the
        // alarm when the device is rebooted.
        ComponentName receiver = new ComponentName(context, BootAlarmReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }



}