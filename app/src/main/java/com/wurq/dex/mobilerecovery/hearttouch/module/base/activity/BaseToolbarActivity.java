package com.wurq.dex.mobilerecovery.hearttouch.module.base.activity;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wurq.dex.mobilerecovery.hearttouch.common.util.ResourcesUtil;
import com.wurq.dex.mobilerecovery.hearttouch.module.base.presenter.BasePresenter;
import com.wurq.dex.mobilerecovery.R;

/**
 * Created by ht-template
 **/
abstract public class BaseToolbarActivity<T extends BasePresenter>
        extends BaseActivity<T> {
    private Toolbar toolbar;
    private int menuResId;
    private boolean isMenuSet = false;
    private View.OnClickListener navigationOnClickListener = null;
    private TextView titleTextView;
    private View navigationBackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_toolbar);
        initContentView();
        initToolbar();
    }

    private void initContentView() {
        rootView = (ViewGroup) findViewById(R.id.root_view);
        contentView = (FrameLayout) findViewById(R.id.content_view);
//        contentView.setOnSlidingFinishListener(this);
        touchView = findViewById(R.id.touch_view);
//        contentView.setTouchView(touchView);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // 默认加上一个退出按钮事件
        toolbar.setTitle("");
        titleTextView = (TextView) toolbar.findViewById(R.id.toolbar_title);
//        navigationBackView = toolbar.findViewById(R.id.navigation_back_view);
//        toolbar.setNavigationIcon(R.layout.view_navigation_back);
        int dimens = (int) ResourcesUtil.getDimen(R.dimen.default_margin);
        toolbar.setPadding(dimens, 0, dimens, 0);
//        toolbar.get
        setSupportActionBar(toolbar);
        setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setRealContentView(@LayoutRes int resId) {
        getLayoutInflater().inflate(resId, contentView);
    }

    public void setLogo(@DrawableRes int resId) {
        toolbar.setLogo(resId);
    }

    //need to setSupportActionBar when setTitle or it will not take effect.
    public void setTitle(String title) {
//        toolbar.setTitle(title);
//        setSupportActionBar(toolbar);
//        resetNavigationOnClickListener();
        titleTextView.setText(title);
    }

    public void setTitle(@StringRes int resId) {
        setTitle(ResourcesUtil.getString(resId));
    }

    public void setSubtitle(String subtitle) {
        toolbar.setSubtitle(subtitle);
    }

    public void setSubtitle(@StringRes int resId) {
        setSubtitle(ResourcesUtil.getString(resId));
    }

    public void setNavigationIcon(@DrawableRes int resId) {
//        toolbar.setNavigationIcon(resId);
        toolbar.setNavigationIcon(R.drawable.selector_back_btn_navigationbar);
    }

    //menu resource id can be correctly set only in onCreate()
    public void setMenu(@MenuRes int resId) {
        isMenuSet = true;
        menuResId = resId;
    }

    public void setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener onMenuItemClick) {
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
    }

    public void setNavigationOnClickListener(View.OnClickListener listener) {
        navigationOnClickListener = listener;
        resetNavigationOnClickListener();
    }

    private void resetNavigationOnClickListener() {
        if (navigationOnClickListener != null) {
            toolbar.setNavigationOnClickListener(navigationOnClickListener);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isMenuSet) {
            getMenuInflater().inflate(menuResId, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }
}
