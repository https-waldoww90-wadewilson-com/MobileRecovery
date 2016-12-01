package com.wurq.dex.mobilerecovery.hearttouch.module.base.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.wurq.dex.mobilerecovery.hearttouch.common.util.ResourcesUtil;
import com.wurq.dex.mobilerecovery.hearttouch.module.base.presenter.BaseFragmentPresenter;
import com.wurq.dex.mobilerecovery.R;

/**
 * Created by ht-template
 **/
abstract public class BaseToolbarFragment<T extends BaseFragmentPresenter>
        extends BaseFragment<T> {
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        initToolBar();
        initContentView();
        return rootView;
    }

    @Override
    protected void inflateRootView(LayoutInflater inflater) {
        rootView = (FrameLayout) inflater.inflate(R.layout.activity_with_toolbar, null);
    }

    private void initToolBar() {
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
    }

    public void setLogo(@DrawableRes int resId) {
        toolbar.setLogo(resId);
    }

    public void setTitle(String title) {
        toolbar.setTitle(title);
    }

    public void setTitle(@StringRes int resId) {
        setTitle(ResourcesUtil.getString(resId));
    }

    public void setSubtitle(String subtitle) {
        toolbar.setSubtitle(subtitle);
    }

    public void setSubTitle(@StringRes int resId) {
        setSubtitle(ResourcesUtil.getString(resId));
    }

    public void setNavigationIcon(@DrawableRes int resId) {
        toolbar.setNavigationIcon(resId);
    }

    //menu resource id can be correctly set only in onCreate()
    public void setMenu(@MenuRes int resId) {
        toolbar.inflateMenu(resId);
    }

    public void setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener onMenuItemClick) {
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
    }

    public void setNavigationOnClickListener(View.OnClickListener listener) {
        toolbar.setNavigationOnClickListener(listener);
    }

    private void initContentView() {
        contentView = (FrameLayout) rootView.findViewById(R.id.content_view);
//        contentView.setOnSlidingFinishListener(this);

        touchView = rootView.findViewById(R.id.touch_view);
//        contentView.setTouchView(touchView);
    }

    public void setRealContentView(@LayoutRes int resId) {
        getActivity().getLayoutInflater().inflate(resId, contentView);
    }

    //没有动画效果
    public void hideToolbar() {
        toolbar.setVisibility(View.INVISIBLE);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) contentView.getLayoutParams();
        layoutParams.setMargins(0, 0, 0, 0);
        contentView.setLayoutParams(layoutParams);
    }

    //没有动画效果
    public void showToolbar() {
        toolbar.setVisibility(View.VISIBLE);
        TypedArray actionbarSizeTypedArray = getActivity().obtainStyledAttributes(new int[]{
                android.R.attr.actionBarSize
        });
        int height = (int) actionbarSizeTypedArray.getDimension(0, 0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) contentView.getLayoutParams();
        layoutParams.setMargins(0, height, 0, 0);
        contentView.setLayoutParams(layoutParams);
    }
}
