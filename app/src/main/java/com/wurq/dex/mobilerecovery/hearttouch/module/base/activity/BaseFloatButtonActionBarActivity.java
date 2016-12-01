package com.wurq.dex.mobilerecovery.hearttouch.module.base.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.wurq.dex.mobilerecovery.hearttouch.common.view.floatbutton.FloatButton;
import com.wurq.dex.mobilerecovery.hearttouch.module.base.presenter.BasePresenter;
import com.wurq.dex.mobilerecovery.R;

/**
 * Created by ht-template
 **/
public abstract class BaseFloatButtonActionBarActivity<T extends BasePresenter>
        extends BaseActionBarActivity<T> {
    protected FloatButton floatButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();
    }

    private void initContentView() {
        FrameLayout rootView = (FrameLayout) findViewById(R.id.root_view);
        floatButton = new FloatButton(this);
        rootView.addView(floatButton);
    }

    public void showFloatButton(boolean isShow) {
        floatButton.showFloatButton(isShow);
    }
}
