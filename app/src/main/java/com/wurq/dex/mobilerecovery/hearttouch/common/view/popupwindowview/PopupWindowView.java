package com.wurq.dex.mobilerecovery.hearttouch.common.view.popupwindowview;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.wurq.dex.mobilerecovery.R;

/**
 * Created by ht-template
 **/
public class PopupWindowView {
    private Context mContext;
    private PopupWindow mPopupWindow;
    private FrameLayout mContentView;
    private boolean mIsOutsideTouchable = true;
    private OutsideTouchListener mTouchListener;

    public PopupWindowView(Context context) {
        this(context, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);
    }

    public PopupWindowView(Context context, int gravity) {
        this(context, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                gravity);
    }

    public PopupWindowView(Context context, int width, int height, int gravity) {
        this.mContext = context;

        LinearLayout view = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.popupwindow_view_center, null);
        view.setGravity(gravity);

        mContentView = (FrameLayout) view.findViewById(R.id.content_view);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
        lp.width = width;
        lp.height = height;
        lp.gravity = gravity;
        mContentView.setLayoutParams(lp);

        view.findViewById(R.id.ll_mask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsOutsideTouchable) {
                    if (mTouchListener != null)
                        mTouchListener.outsideTouchListener();
                    else {
                        dismiss();
                    }
                }
            }
        });
        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景（很神奇的）
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setClippingEnabled(false);//允许弹出窗口超出屏幕范围
        mPopupWindow.setAnimationStyle(R.style.PopWindowAnimBottom);//设置默认渐隐动画
    }

    public void addSubView(View view, FrameLayout.LayoutParams lp) {
        mContentView.addView(view, lp);
        //view是显示内容的,设置onTouch返回true,点击在范围内的事件不会触发dismiss
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    //下拉式 弹出 pop菜单 parent 右下角
    public void showAsDropDown(View parent) {
        showAsDropDown(parent, 0, mContext.getResources().getDimensionPixelSize(R.dimen.popmenu_yoff));
    }

    public ViewGroup getmContentView() {
        return mContentView;
    }

    /**
     * 添加动画
     */
    public void setAnimationStyle(int animationStyle) {
        mPopupWindow.setAnimationStyle(animationStyle);
    }

    //下拉式 弹出 pop菜单 parent 右下角
    public void showAsDropDown(View parent, int xOffset, int yOffset) {
        //保证尺寸是根据屏幕像素密度来的
        mPopupWindow.showAsDropDown(parent, xOffset, yOffset);
        //使其聚焦
        mPopupWindow.setFocusable(true);
        //设置允许在外点击消失
        mPopupWindow.setOutsideTouchable(true);
        //刷新状态
        mPopupWindow.update();
    }

    /**
     * 显示在父View正中央
     */
    public void showInCenter(View parent) {
        //保证尺寸是根据屏幕像素密度来的
        mPopupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
        //使其聚焦
        mPopupWindow.setFocusable(true);
        //设置允许在外点击消失
        mPopupWindow.setOutsideTouchable(true);
        //刷新状态
        mPopupWindow.update();
    }

    public void showInCenter(View parent, boolean isOutsideTouchable) {
        //保证尺寸是根据屏幕像素密度来的
        mPopupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
        //使其聚焦
        mPopupWindow.setFocusable(true);
        //设置允许在外点击消失
        mPopupWindow.setOutsideTouchable(isOutsideTouchable);
        mIsOutsideTouchable = isOutsideTouchable;
        //刷新状态
        mPopupWindow.update();
    }

    public int getHeight() {
        return mPopupWindow.getHeight();
    }

    public void setHeight(int height) {
        mPopupWindow.setHeight(height);
    }

    public void showAtLocation(View parent, int gravity, int x, int y) {
        showAtLocation(parent, gravity, x, y, false);
    }

    public void showAtLocation(View parent, int gravity, int x, int y, boolean isOutsideTouchable) {
        //保证尺寸是根据屏幕像素密度来的
        mPopupWindow.showAtLocation(parent, gravity, x, y);
        //使其聚焦
        mPopupWindow.setFocusable(true);
        //设置允许在外点击消失
        mPopupWindow.setOutsideTouchable(isOutsideTouchable);
        mIsOutsideTouchable = isOutsideTouchable;
        //刷新状态
        mPopupWindow.update();
    }

    public void setFocusable(boolean focusable) {
        mPopupWindow.setFocusable(focusable);
        mPopupWindow.setOutsideTouchable(focusable);
        mPopupWindow.update();
    }

    public boolean isShowing() {
        return mPopupWindow.isShowing();
    }

    public void dismiss() {
        mPopupWindow.dismiss();
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
        mPopupWindow.setOnDismissListener(listener);
    }

    public void setOutSideTouchListener(OutsideTouchListener touchListener) {
        mTouchListener = touchListener;
    }

    public interface OutsideTouchListener {
        void outsideTouchListener();
    }
}
