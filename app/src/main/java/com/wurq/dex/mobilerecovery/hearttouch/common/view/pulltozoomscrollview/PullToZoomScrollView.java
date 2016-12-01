package com.wurq.dex.mobilerecovery.hearttouch.common.view.pulltozoomscrollview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ScrollView;

import com.wurq.dex.mobilerecovery.R;

/**
 * ScrollView，下拉时设置的view会变高，放开后view变回以前的高度，弹簧效果
 * 为了方便复用，这里的view不需要是scrollView的child
 * Created by ht-template
 **/
public class PullToZoomScrollView extends ScrollView {
    private final float[] mScaleRange = {0.8f, 0.6f, 0.4f, 0.2f, 0.1f};
    private float mScale = 0.8f;
    private int mMaxDistance = 142;
    private int mTotalDistance = 0;
    private Interpolator mInterpolator;
    private int mAnimateDuration;
    private View mScaleView;
    private int mScaleViewOriginHeight;
    private float mLastY = 0;
    private float[] mScaleToDistanceRange;

    public PullToZoomScrollView(Context context) {
        super(context);
        init();
    }

    public PullToZoomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttribute(attrs);
        init();
    }

    private void initAttribute(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PullToZoomScrollView);
        try {
            mMaxDistance = typedArray.getDimensionPixelSize(R.styleable.PullToZoomScrollView_maxInstance, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 71, getResources().getDisplayMetrics()));
            mAnimateDuration = typedArray.getInt(R.styleable.PullToZoomScrollView_animateDuration, 200);
        } finally {
            typedArray.recycle();
        }
    }

    private void init() {
        setOverScrollMode(OVER_SCROLL_NEVER);
        mInterpolator = new DecelerateInterpolator();
        mAnimateDuration = 200;
        initScaleData();
    }

    private void initScaleData() {
        float gap = mMaxDistance / 5.0f;
        mScaleToDistanceRange = new float[]{
                gap,
                gap * 2,
                gap * 3,
                gap * 4,
                mMaxDistance
        };
    }

    /**
     * 获取下拉最大距离
     *
     * @return
     */
    public int getMaxDistance() {
        return mMaxDistance;
    }

    /**
     * 设置下拉最大距离
     *
     * @param maxDistance
     */
    public void setMaxDistance(int maxDistance) {
        mMaxDistance = maxDistance;
    }

    /**
     * 获取回弹动画插值器，默认为减速
     *
     * @return
     */
    public Interpolator getInterpolator() {
        return mInterpolator;
    }

    /**
     * 设置回弹动画插值器，默认为减速
     *
     * @param interpolator
     */
    public void setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
    }

    /**
     * 设置回弹动画时间，默认为200
     *
     * @return
     */
    public int getAnimateDuration() {
        return mAnimateDuration;
    }

    /**
     * 获取回弹动画时间
     *
     * @param animateDuration
     */
    public void setAnimateDuration(int animateDuration) {
        mAnimateDuration = animateDuration;
    }

    /**
     * 获取放大的view
     *
     * @return
     */
    public View getScaleView() {
        return mScaleView;
    }

    /**
     * 设置放大的view
     *
     * @param view
     */
    public void setScaleView(View view) {
        if (view != null && view != mScaleView) {
            mScaleView = view;
            mScaleView.post(new Runnable() {
                @Override
                public void run() {
                    mScaleViewOriginHeight = mScaleView.getMeasuredHeight();
                }
            });
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mScaleView == null) {
            return super.dispatchTouchEvent(ev);
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                float distance = ev.getY() - mLastY;
                mLastY = ev.getY();

                //ScrollView 处于滚动状态，则不做处理
                if (getScrollY() != 0 || distance <= 0) {
                    break;
                }
                mTotalDistance = Math.min(mTotalDistance, mMaxDistance);
                updateScale(mTotalDistance);
                updateViewHeight(mScaleView, (int) (mScaleView.getMeasuredHeight() + distance * mScale));
                mTotalDistance += distance * mScale;
                //处理此次move，取消子view的down事件
                //用cancel会引发bug，暂时先注释，后续有空优化
//                ev.setAction(MotionEvent.ACTION_CANCEL);
                break;
            case MotionEvent.ACTION_UP:
                if (mTotalDistance > 0) {
                    zoomToOrigin();
                }
                mTotalDistance = 0;

                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    private void updateViewHeight(View view, int height) {
        if (view == null) {
            return;
        }
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.height = height;
        view.setLayoutParams(lp);
    }

    /**
     * 回弹动画
     */
    private void zoomToOrigin() {
        ValueAnimator animator = ValueAnimator.ofInt(mScaleView.getMeasuredHeight(), mScaleViewOriginHeight);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                updateViewHeight(mScaleView, (int) valueAnimator.getAnimatedValue());
            }
        });
        animator.setDuration(mAnimateDuration);
        animator.setInterpolator(mInterpolator);
        animator.start();
    }

    /**
     * 根据 totalDistance 的范围获取当前增量对应的比例
     *
     * @param totalDistance
     */
    private void updateScale(int totalDistance) {
        int i = 0;
        while (i < 5) {
            if (totalDistance < mScaleToDistanceRange[i]) {
                mScale = mScaleRange[i];
                return;
            }
            ++i;
        }
        mScale = 0;
    }
}
