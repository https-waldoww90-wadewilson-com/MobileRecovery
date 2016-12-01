package com.wurq.dex.mobilerecovery.hearttouch.module.base.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.wurq.dex.mobilerecovery.hearttouch.module.base.presenter.BasePresenter;
import com.wurq.dex.mobilerecovery.R;

/**
 * Created by ht-template
 **/
public abstract class BaseBlankActivity<T extends BasePresenter>
        extends BaseActivity<T> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        initContentView();
    }

    private void initContentView() {
        rootView = (ViewGroup) findViewById(R.id.root_view);
        contentView = (FrameLayout) findViewById(R.id.content_view);
//        contentView.setOnSlidingFinishListener(this);

        touchView = findViewById(R.id.touch_view);
//        contentView.setTouchView(touchView);
    }

    /**
     * Inflate a content view for the activity.
     *
     * @param resId ID for an XML layout resource as the content view
     */
    public void setRealContentView(@LayoutRes int resId) {
        getLayoutInflater().inflate(resId, contentView);
    }
}
