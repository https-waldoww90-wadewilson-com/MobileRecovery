package com.wurq.dex.mobilerecovery.hearttouch.module.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.wurq.dex.mobilerecovery.hearttouch.common.util.ResourcesUtil;
import com.wurq.dex.mobilerecovery.hearttouch.module.base.presenter.BaseFragmentPresenter;
import com.wurq.dex.mobilerecovery.hearttouch.module.base.view.IShowErrorView;
import com.wurq.dex.mobilerecovery.hearttouch.module.base.view.POCTBlankView;
import com.wurq.dex.mobilerecovery.hearttouch.module.base.view.POCTErrorView;

import java.lang.ref.WeakReference;

//import com.squareup.leakcanary.RefWatcher;

/**
 * Created by ht-template
 **/
public abstract class BaseFragment<T extends BaseFragmentPresenter>
        extends Fragment
        implements/* OnSlidingFinishListener,*/
        IShowErrorView {

    protected T presenter;
    protected FrameLayout rootView;
    protected WeakReference<View> rootViewRef;
    /**
     * 用于监听滑动的view
     */
    protected FrameLayout contentView;
    protected View touchView;
    //错误页和空白页
    POCTErrorView mPOCTErrorView;
    POCTBlankView mPOCTBlankView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (presenter != null) {
            presenter.onAttach();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
        if (presenter != null) {
            presenter.onCreate();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        inflateRootView(inflater);
        if (presenter != null) {
            presenter.onCreateView();
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (presenter != null) {
            presenter.onActivityCreated(savedInstanceState);
        }
    }

    /**
     * Inflate a content view for the activity.
     *
     * @param resId ID for an XML layout resource as the content view
     */
    public void setRealContentView(@LayoutRes int resId) {
        getActivity().getLayoutInflater().inflate(resId, contentView);
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
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.onDestroyView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }

//        RefWatcher refWatcher = PoctApplication.getRefWatcher(getActivity());
//        refWatcher.watch(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (presenter != null) {
            presenter.onDetach();
        }
    }

    abstract protected void initPresenter();

  /*  @Override
    public void onSlidingFinish() {
        FragmentManager fm = getFragmentManager();
        if (!fm.popBackStackImmediate()) {
            getActivity().finish();
        }
        contentView.resetPosition();
    }*/

    protected void inflateRootView(LayoutInflater inflater) {
    }

   /* public void setSlidingEnabled(boolean isSlidingEnabled) {
        if (contentView != null) {
            contentView.setIsSlidingEnabled(isSlidingEnabled);
        }
    }

    public boolean isSlidingFinishEnabled() {
        return contentView != null && contentView.isSlidingEnabled();
    }*/

    //严选相关代码请添加在上下的注释之间
    //初始化空白页
    public void initBlankView(@DrawableRes int iconResId, @StringRes int stringResId) {
        if (mPOCTBlankView == null) {
            Context context = getContext();
            if (context == null) {
                return;
            }
            mPOCTBlankView = POCTBlankView.getYXBlankView(context);
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
        if (mPOCTErrorView == null) {
            Context context = getContext();
            if (context == null) {
                return;
            }
            mPOCTErrorView = POCTErrorView.getYXErrorView(context);
            mPOCTErrorView.setVisibility(View.GONE);
            rootView.addView(mPOCTErrorView);
        }
        mPOCTErrorView.setBlankHint(hintText);
        mPOCTErrorView.setBlankIconDrawable(ResourcesUtil.getDrawable(iconResId));
    }

    //错误页一般是通用的，所以不需要初始化
    //同理如果隐藏了导航栏的话可能padding是不同的，需要那contentView的padding
    @Override
    public void showErrorView(boolean needShow) {
        if (mPOCTErrorView == null) {
            return;
        }
        showBlankOrError(mPOCTErrorView, needShow);
    }

    @Override
    //提供设置点击重新加载的接口
    public void setReloadClickListener(View.OnClickListener listener) {
        if (mPOCTErrorView != null) {
            mPOCTErrorView.setReloadClickListener(listener);
        }
    }

    private void showBlankOrError(View view, boolean needShow) {
        if (mPOCTBlankView != null) {
            mPOCTBlankView.setVisibility(View.GONE);
        }
        if (mPOCTErrorView != null) {
            mPOCTErrorView.setVisibility(View.GONE);
        }
        if (needShow) {
            view.setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop(), contentView.getPaddingRight(), contentView.getPaddingBottom());
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }
    //严选相关代码请添加在上下的注释之间
}
