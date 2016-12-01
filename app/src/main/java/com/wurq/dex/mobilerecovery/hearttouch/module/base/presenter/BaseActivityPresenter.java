package com.wurq.dex.mobilerecovery.hearttouch.module.base.presenter;

import com.wurq.dex.mobilerecovery.hearttouch.module.base.activity.BaseActivity;

/**
 * Created by ht-template
 **/
public abstract class BaseActivityPresenter<T extends BaseActivity>
        extends BasePresenter<T> {
    public BaseActivityPresenter(T target) {
        super(target);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
