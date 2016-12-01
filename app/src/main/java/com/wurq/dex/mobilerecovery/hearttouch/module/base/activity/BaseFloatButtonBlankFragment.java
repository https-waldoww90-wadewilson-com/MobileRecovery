package com.wurq.dex.mobilerecovery.hearttouch.module.base.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wurq.dex.mobilerecovery.hearttouch.common.view.floatbutton.FloatButton;
import com.wurq.dex.mobilerecovery.hearttouch.module.base.presenter.BaseFragmentPresenter;

/**
 * Created by ht-template
 **/
public abstract class BaseFloatButtonBlankFragment<T extends BaseFragmentPresenter>
        extends BaseBlankFragment<T> {
    protected FloatButton floatButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        initContentView();
        return rootView;
    }

    private void initContentView() {
        floatButton = new FloatButton(getActivity());
        rootView.addView(floatButton);
    }

    public void showFloatButton(boolean isShow) {
        floatButton.showFloatButton(isShow);
    }
}
