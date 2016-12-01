package com.wurq.dex.mobilerecovery.hearttouch.module.base.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.wurq.dex.mobilerecovery.hearttouch.common.view.floatbutton.FloatButton;
import com.wurq.dex.mobilerecovery.hearttouch.module.base.presenter.BaseFragmentPresenter;
import com.wurq.dex.mobilerecovery.R;

/**
 * Created by ht-template
 **/
public abstract class BaseFloatButtonBlankActivity<T extends BaseFragmentPresenter>
        extends BaseBlankActivity<T> {
    protected FloatButton floatButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
