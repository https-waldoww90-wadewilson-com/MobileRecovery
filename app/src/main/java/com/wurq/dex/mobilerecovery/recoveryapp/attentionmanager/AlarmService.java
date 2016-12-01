package com.wurq.dex.mobilerecovery.recoveryapp.attentionmanager;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.wurq.dex.mobilerecovery.R;

public class AlarmService extends IntentService {

    private static final String TAG = "AlarmService";

    PowerManager.WakeLock wakeLock = null;

    private NotificationManager mNotificationManager;
    private String mMessage;
    private int mMillis;
    NotificationCompat.Builder builder;
    public AlarmService() {
        super("com.wurq.dex.mobilerecovery.recoveryapp.attentionmanager.AlarmService");
    }

    //acquire wake lock
    private void acquireWakeLock()
    {
        if (null == wakeLock)
        {
            PowerManager pm = (PowerManager)this.getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK| PowerManager.ON_AFTER_RELEASE,
                    "AttentionService");
            if (null != wakeLock)
            {
                wakeLock.acquire();
            }
        }
    }

    //release wake lock
    private void releaseWakeLock()
    {
        if (null != wakeLock)
        {
            wakeLock.release();
            wakeLock = null;
        }
    }

    @Override
    public void onCreate() {
        Log.d(TAG,"AlarmService -> onCreate, Thread ID: " + Thread.currentThread().getId());

        super.onCreate();
    }

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.d(TAG, "AlarmService -> onStartCommand, startId: " + startId
//                + ", Thread ID: " + Thread.currentThread().getId());
//        acquireWakeLock();
////        setBootAlarm(this);
//
////        postAlarm();
////        sendNotification("test recismg");
////        timeAttention(17,14);
////        setAlarm();
//        return START_STICKY;
//    }


    @Override
    public IBinder onBind(Intent intent) {
        //
        Log.d(TAG, "AlarmService -> onBind, Thread ID: " + Thread.currentThread().getId());
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "AlarmService -> onDestroy, Thread ID: " + Thread.currentThread().getId());
//        cancelBootAlarm(this);
        releaseWakeLock();
        super.onDestroy();
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG,"AlarmService onHandleIntent enering... " );
        Log.d(TAG,"AlarmService -> onCreate, Thread ID: " + Thread.currentThread().getId());
        acquireWakeLock();

        // The reminder message the user set.
        mMessage = intent.getStringExtra(CommonConstants.EXTRA_MESSAGE);
        Log.d(TAG,"AlarmService onHandleIntent mMessage =  " + mMessage);
        // The timer duration the user set. The default is 10 seconds.
        mMillis = intent.getIntExtra(CommonConstants.EXTRA_TIMER,
                CommonConstants.DEFAULT_TIMER_DURATION);
//        NotificationManager nm = (NotificationManager)
//                getSystemService(NOTIFICATION_SERVICE);
        sendNotification(mMessage);
        // TODO action
/*
        String action = intent.getAction();
        // This section handles the 3 possible actions:
        // ping, snooze, and dismiss.
        if(action.equals(CommonConstants.ACTION_PING)) {
            sendNotification( mMessage);
        } else if (action.equals(CommonConstants.ACTION_SNOOZE)) {
            nm.cancel(CommonConstants.NOTIFICATION_ID);
//            Log.d(CommonConstants.DEBUG_TAG, getString(R.string.app_name));
//             Sets a snooze-specific "done snoozing" message.
            sendNotification( getString(R.string.app_name));

        } else if (action.equals(CommonConstants.ACTION_DISMISS)) {
            nm.cancel(CommonConstants.NOTIFICATION_ID);
        }*/
    }

    // Post a notification indicating .
//    private NotificationManager mNotificationManager;

    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, AlarmService.class), 0);

        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.app_name))
                        .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(msg))
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(1, mBuilder.build());
    }

}
