package com.wurq.dex.mobilerecovery.hearttouch.common.view.slidingfinishlayout;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.wurq.dex.mobilerecovery.hearttouch.common.util.ScreenUtil;

/**
 * Created by ht-template
 **/
public class CubeRotateFinishLayout extends RelativeLayout implements
        View.OnTouchListener {
    /**
     * SildingFinishLayout布局的父布局
     */
    private ViewGroup mParentView;
    /**
     * 处理滑动逻辑的View
     */
    private View touchView;
    /**
     * 滑动的最小距离
     */
    private int mTouchSlop;
    /**
     * 按下点的X坐标
     */
    private int downX;
    /**
     * 按下点的Y坐标
     */
    private int downY;
    /**
     * 临时存储X坐标
     */
    private int tempX;
    /**
     * SildingFinishLayout的宽度
     */
    private int viewWidth;
    /**
     * 记录是否正在滑动
     */
    private boolean isSliding;

    private OnSlidingFinishListener onSlidingFinishListener;
    private boolean isFinish;

    private boolean isSlidingEnabled = true;


    private Matrix myMatrix;
    private Camera camera;
    private float interpolate = 0;

//    private Matrix preViewMatrix;
//    private boolean isShowCubePrevView = true;

    public CubeRotateFinishLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CubeRotateFinishLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        myMatrix = new Matrix();
//        preViewMatrix = new Matrix();
        camera = new Camera();
        applyTransformation(0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            // 获取SildingFinishLayout所在布局的父布局
            mParentView = (ViewGroup) this.getParent();
            viewWidth = this.getWidth();
        }
    }

    /**
     * 设置OnSlidingFinishListener, 在onSlidingFinish()方法中finish Activity
     *
     * @param onSlidingFinishListener
     */
    public void setOnSlidingFinishListener(OnSlidingFinishListener onSlidingFinishListener) {
        this.onSlidingFinishListener = onSlidingFinishListener;
    }

    public View getTouchView() {
        return touchView;
    }

    /**
     * 设置Touch的View
     *
     * @param touchView
     */
    public void setTouchView(View touchView) {
        this.touchView = touchView;
        touchView.setOnTouchListener(this);
    }

    /**
     * 滚动出界面
     */
    private void scrollRight() {
        long duration = 400 - (long) interpolate * 400;
        ValueAnimator animator = ValueAnimator.ofFloat(interpolate, 1).
                setDuration(duration);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (onSlidingFinishListener != null && isFinish) {
                    onSlidingFinishListener.onSlidingFinish();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                interpolate = (float) animation.getAnimatedValue();
                applyTransformation(interpolate);
            }
        });
        animator.start();
    }

    /**
     * 滚动到起始位置
     */
    private void scrollOrigin() {
        long duration = (long) (interpolate * 400);
        ValueAnimator animator = ValueAnimator.ofFloat(interpolate, 0).
                setDuration(duration);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                interpolate = (float) animation.getAnimatedValue();
                applyTransformation(interpolate);
            }
        });
        animator.start();
    }

    /**
     * touch的View是否是AbsListView， 例如ListView, GridView等其子类
     *
     * @return
     */
    private boolean isTouchOnAbsListView() {
        return touchView instanceof AbsListView ? true : false;
    }

    /**
     * touch的view是否是ScrollView或者其子类
     *
     * @return
     */
    private boolean isTouchOnScrollView() {
        return touchView instanceof ScrollView ? true : false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (isSlidingEnabled) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = tempX = (int) event.getRawX();
                    downY = (int) event.getRawY();
                    setBackgroundColor(Color.TRANSPARENT);
                    break;
                case MotionEvent.ACTION_MOVE:
                    int moveX = (int) event.getRawX();
                    int deltaX = tempX - moveX;
                    tempX = moveX;
                    if (Math.abs(moveX - downX) > mTouchSlop
                            && Math.abs((int) event.getRawY() - downY) < mTouchSlop) {
                        isSliding = true;

                        // 若touchView是AbsListView，
                        // 则当手指滑动，取消item的点击事件，不然我们滑动也伴随着item点击事件的发生
                        if (isTouchOnAbsListView()) {
                            MotionEvent cancelEvent = MotionEvent.obtain(event);
                            cancelEvent.setAction(MotionEvent.ACTION_CANCEL
                                    | (event.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                            v.onTouchEvent(cancelEvent);
                        }
                    }

                    if (moveX - downX >= 0 && isSliding) {

                        float deltaAngle = (float) Math.PI * deltaX / getWidth();
                        myMatrix.postRotate(deltaAngle, 0, 1);

                        interpolate = 1.0f * (moveX - downX) / getWidth();
                        applyTransformation(interpolate);

                        //mParentView.scrollBy(deltaX, 0);

                        // 屏蔽在滑动过程中ListView ScrollView等自己的滑动事件
                        if (isTouchOnScrollView() || isTouchOnAbsListView()) {
                            return true;
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    isSliding = false;
                    if (interpolate >= 0.5f) {
                        isFinish = true;
                        scrollRight();
                    } else {
                        scrollOrigin();
                        isFinish = false;
                    }
                    break;
            }
        }

        // 假如touch的view是AbsListView或者ScrollView 我们处理完上面自己的逻辑之后
        // 再交给AbsListView, ScrollView自己处理其自己的逻辑
        if (isTouchOnScrollView() || isTouchOnAbsListView()) {
            return v.onTouchEvent(event);
        }

        // 其他的情况直接返回true
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        View preView = AppProfile.getPrevActivityView(this);
//        if (preView != null && isSlidingEnabled() && isSliding) {
//            canvas.setMatrix(preViewMatrix);
//            preView.draw(canvas);
//        }

        canvas.setMatrix(myMatrix);
        super.onDraw(canvas);
    }


    public void resetPosition() {
        interpolate = 0;
        applyTransformation(interpolate);
    }

    public boolean isSlidingEnabled() {
        return isSlidingEnabled;
    }

    public void setIsSlidingEnabled(boolean isSlidingEnabled) {
        this.isSlidingEnabled = isSlidingEnabled;
    }

    protected void applyTransformation(float interpolatedTime) {
        float rotate = (90 * interpolatedTime);
        int width = getWidth();
        int height = getHeight();

        camera.save();
        camera.translate((-width + width * interpolatedTime), 0, 0);
        camera.rotateY(rotate);
        camera.getMatrix(myMatrix);
        camera.restore();

        myMatrix.postTranslate(width, height / 2);
        myMatrix.preTranslate(0, -height / 2 + ScreenUtil.getStatusBarHeight());

        ///////
//        if (isShowCubePrevView && AppProfile.getPrevActivityView(this) != null) {
//            float preRotate = (-90 + 90 * interpolatedTime);
//            camera.save();
//            camera.translate((interpolatedTime * width), 0, 0);
//            camera.rotateY(preRotate);
//            camera.getMatrix(preViewMatrix);
//            camera.restore();
//
//            preViewMatrix.postTranslate(0, height / 2);
//            preViewMatrix.preTranslate(- width, - height / 2 + ScreenUtil.getStatusBarHeight());
//        }

        postInvalidate();
    }
}
