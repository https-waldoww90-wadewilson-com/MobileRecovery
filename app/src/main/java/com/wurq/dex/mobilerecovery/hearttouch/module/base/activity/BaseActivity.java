package com.wurq.dex.mobilerecovery.hearttouch.module.base.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.wurq.dex.mobilerecovery.R;
import com.wurq.dex.mobilerecovery.hearttouch.common.util.ResourcesUtil;
import com.wurq.dex.mobilerecovery.hearttouch.module.base.presenter.BasePresenter;
import com.wurq.dex.mobilerecovery.hearttouch.module.base.view.IShowErrorView;
import com.wurq.dex.mobilerecovery.hearttouch.module.base.view.POCTBlankView;
import com.wurq.dex.mobilerecovery.hearttouch.module.base.view.POCTErrorView;

//import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by ht-template
 **/
public abstract class BaseActivity<T extends BasePresenter>
        extends AppCompatActivity implements
        IShowErrorView {
    private static final String TAG = "BaseActivity";

    protected T presenter;
    /**
     * 用于监听滑动的view
     */
    protected FrameLayout contentView;
    protected View touchView;
    protected ViewGroup rootView;
    /**
     * 用于监听inflater view
     */
    protected LayoutInflater layoutInflater;
//    SystemBarTintManager tintManager;
    //错误页和空白页
    POCTErrorView mRevoceryErrorView;
    POCTBlankView mPOCTBlankView;

    /**
     * The Handler that gets information back from the ConnectHelper
     */
//    public Handler mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutInflater = LayoutInflater.from(this);
        setStatueBarColor(R.color.black);
        initPresenter();
        if (presenter != null) {
            presenter.onCreate();
        }

//        mHandler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                ProcessShow( msg );
//            }
//        };

//        ConnectHelper.getInstance(AppProfile.getContext(),mHandler);
    }

    /**
     * Inflate a content view for the activity.
     *
     * @param resId ID for an XML layout resource as the content view
     */
    public void setRealContentView(@LayoutRes int resId) {
        getLayoutInflater().inflate(resId, contentView);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    protected void disableScreenCapture() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (presenter != null) {
            presenter.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.onStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {//MENU键
            //监控/拦截菜单键
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    abstract protected void initPresenter();

    public void setStatueBarColor(@ColorRes int colorResId) {
        int color = ResourcesUtil.getColor(colorResId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setTranslucentStatus(true);
            }
//            // 创建状态栏的管理实例
//            if (tintManager == null) {
//                tintManager = new SystemBarTintManager(this);
//                // 激活状态栏设置
//                tintManager.setStatusBarTintEnabled(true);
//            }
//            tintManager.setTintColor(color);
        }
    }

    //严选相关代码请添加在上下的注释之间
    //初始化空白页
    public void initBlankView(@DrawableRes int iconResId, @StringRes int stringResId) {
        if (mPOCTBlankView == null) {
            mPOCTBlankView = POCTBlankView.getYXBlankView(this);
            mPOCTBlankView.setVisibility(View.GONE);
            rootView.addView(mPOCTBlankView);
        }
        mPOCTBlankView.setBlankHint(ResourcesUtil.getString(stringResId));
        mPOCTBlankView.setBlankIconDrawable(ResourcesUtil.getDrawable(iconResId));
    }

    //必须先init一下，因为每个空白页的资源是不同的
    //如果隐藏了导航栏的话可能padding是不同的，需要那contentView的padding
    public void showBlankView(boolean needShow) {
        if (mPOCTBlankView == null) {
            return;
        }
        showBlankOrError(mPOCTBlankView, needShow);
    }

    @Override
    public void initErrorView(@DrawableRes int iconResId, String hintText) {
        if (mRevoceryErrorView == null) {
            mRevoceryErrorView = POCTErrorView.getYXErrorView(this);
            mRevoceryErrorView.setVisibility(View.GONE);
            rootView.addView(mRevoceryErrorView);
        }
        mRevoceryErrorView.setBlankHint(hintText);
        mRevoceryErrorView.setBlankIconDrawable(ResourcesUtil.getDrawable(iconResId));
    }

    //错误页一般是通用的，所以不需要初始化
    //同理如果隐藏了导航栏的话可能padding是不同的，需要那contentView的padding
    @Override
    public void showErrorView(boolean needShow) {
        if (mRevoceryErrorView == null) {
            return;
        }
        showBlankOrError(mRevoceryErrorView, needShow);
    }

    //提供设置点击重新加载的接口
    @Override
    public void setReloadClickListener(View.OnClickListener listener) {
        if (mRevoceryErrorView != null) {
            mRevoceryErrorView.setReloadClickListener(listener);
        }
    }

    private void showBlankOrError(View view, boolean needShow) {
        if (mPOCTBlankView != null) {
            mPOCTBlankView.setVisibility(View.GONE);
        }
        if (mRevoceryErrorView != null) {
            mRevoceryErrorView.setVisibility(View.GONE);
        }
        if (needShow) {
            view.setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop(), contentView.getPaddingRight(), contentView.getPaddingBottom());
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }



}