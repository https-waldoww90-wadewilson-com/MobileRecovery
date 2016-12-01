package com.wurq.dex.mobilerecovery.hearttouch.common.view.progressdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.wurq.dex.mobilerecovery.hearttouch.common.util.ScreenUtil;
import com.wurq.dex.mobilerecovery.R;

/**
 * Created by ht-template
 **/
public class LoadDataProgressDialog extends Dialog {
    private TextView tvLoding;
    //    private ArcProgressbar pgLoading;
    private AnimationDrawable loadAnimation;
    private boolean isCancelable = false;

    private boolean init;

    public LoadDataProgressDialog(Context context, boolean init) {
        this(context, R.style.CustomProgressDialog, init);
    }

    public LoadDataProgressDialog(Context context, int theme, boolean init) {
        super(context, theme);
        this.init = init;
        if (init) {//第一次进入界面加载数据
            setContentView(R.layout.view_init_loadding);
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.gravity = Gravity.TOP;
            layoutParams.height = ScreenUtil.getDisplayHeight() - ScreenUtil.getStatusBarHeight();
            layoutParams.width = ScreenUtil.screenWidth;
            getWindow().setAttributes(layoutParams);
            getWindow().setWindowAnimations(R.style.popWindowAnimBottom);
        } else {//在当前界面进行网络请求操作
            setContentView(R.layout.view_current_loadding);
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.height = ScreenUtil.getDisplayHeight() - ScreenUtil.getStatusBarHeight();
            layoutParams.width = ScreenUtil.screenWidth;
            getWindow().setAttributes(layoutParams);
            getWindow().setWindowAnimations(R.style.popWindowAnimBottom);
        }
//        pgLoading = (ArcProgressbar) findViewById(R.id.loading_image);
        tvLoding = (TextView) findViewById(R.id.loading_text);
        loadAnimation = (AnimationDrawable) ((ImageView) findViewById(R.id.img_loading)).getDrawable();

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                loadAnimation.stop();
            }
        });
    }

    public void setMessage(String message) {
        tvLoding.setVisibility(View.VISIBLE);
        tvLoding.setText(message);
    }

    public void setMessage(@StringRes int messageResId) {
        tvLoding.setVisibility(View.VISIBLE);
        tvLoding.setText(messageResId);
    }

    public boolean isInit() {
        return init;
    }

    public void hideMessage() {
        tvLoding.setVisibility(View.GONE);
    }

    public void startRotate() {
        loadAnimation.start();
    }

    public void stopRotate() {
        loadAnimation.stop();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
       /* if (!hasFocus) {
            dismiss();
            stopRotate();
        }*/
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void show() {
        super.show();
    }

    public boolean isCancelable() {
        return isCancelable;
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
        isCancelable = flag;
    }
}
