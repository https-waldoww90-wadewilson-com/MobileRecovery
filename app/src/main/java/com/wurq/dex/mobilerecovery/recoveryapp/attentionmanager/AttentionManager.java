package com.wurq.dex.mobilerecovery.recoveryapp.attentionmanager;

import android.content.Context;
import android.content.Intent;

/**
 * Created by wurongqiu on 16/7/25.
 */
public class AttentionManager {
    private static AttentionManager smAttentionManager = null;
    private Context mContext;
    private  AlarmReceiver mAlarm;

    private AttentionManager(){
    }

    private AttentionManager(Context context){
        mContext = context;
        mAlarm = new AlarmReceiver();

    }

    public void postAlarm()
    {
        mAlarm.postAlarm( mContext);
    }

    public void DestroySelf() {
        if(null != smAttentionManager ) {
            synchronized(AttentionManager.class){
                Intent intent = new Intent( mContext, AlarmService.class);
                mContext.stopService(intent);
                smAttentionManager = null;
            }
        }
    }

    public static AttentionManager getInstance(Context context){
        if(null == smAttentionManager ) {
            synchronized(AttentionManager.class){
                if(null == smAttentionManager)
                    smAttentionManager = new AttentionManager(context);
            }
        }

        return smAttentionManager;
    }

//    public
}
