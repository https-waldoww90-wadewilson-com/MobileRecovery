package com.wurq.dex.mobilerecovery.hearttouch.module.base.view;

import android.support.annotation.DrawableRes;
import android.view.View;

/**
 * Created by ht-template
 **/
public interface IShowErrorView {
    void initErrorView(@DrawableRes int iconResId, String hintText);

    void showErrorView(boolean needShow);

    void setReloadClickListener(View.OnClickListener listener);
}
