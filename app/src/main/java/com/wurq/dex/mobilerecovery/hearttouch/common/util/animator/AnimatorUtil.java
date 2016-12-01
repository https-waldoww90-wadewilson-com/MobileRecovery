package com.wurq.dex.mobilerecovery.hearttouch.common.util.animator;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by hzdengliuyitai on 2016/9/12.
 */
public class AnimatorUtil {

    public static ObjectAnimator getRepeatRandomTranslationX(View view, float distance, long duration) {
        ObjectAnimator mAnimator = ObjectAnimator.ofFloat(view, "translationX", 0, distance, 0);
        mAnimator.setDuration(duration);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        return mAnimator;
    }

    public static ObjectAnimator getRepeatRandomTranslationX(View mLoadingView, View mLoadingParentView, long duration){
        float X  = mLoadingView.getX();
        ObjectAnimator mAnimator = ObjectAnimator.ofFloat(mLoadingView,"translationX",X,mLoadingParentView.getRight()-mLoadingView.getRight(),mLoadingParentView.getLeft()-mLoadingView.getLeft(),X);
        mAnimator.setDuration(duration);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        return mAnimator;
    }
}
