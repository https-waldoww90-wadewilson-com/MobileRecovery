package com.wurq.dex.mobilerecovery.hearttouch.common.util;

import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.nineoldandroids.animation.Animator;

/**
 * Created by ht-template
 **/
public class AnimationUtil {
    private static Interpolator mAccelerateDecelerateInterpolator;
    private static Interpolator mAccelerateInterpolator;
    private static Interpolator mDecelerateInterpolator;
    private static Interpolator mLinearInterpolator;

    public static synchronized Interpolator getAccelerateDecelerateInterpolator() {
        if (mAccelerateDecelerateInterpolator == null)
            mAccelerateDecelerateInterpolator = new AccelerateDecelerateInterpolator();
        return mAccelerateDecelerateInterpolator;
    }

    public static synchronized Interpolator getAccelerateInterpolator() {
        if (mAccelerateInterpolator == null)
            mAccelerateInterpolator = new AccelerateInterpolator();
        return mAccelerateInterpolator;
    }

    public static synchronized Interpolator getDecelerateInterpolator() {
        if (mDecelerateInterpolator == null)
            mDecelerateInterpolator = new DecelerateInterpolator();
        return mDecelerateInterpolator;
    }

    public static synchronized Interpolator getLinearInterpolator() {
        if (mLinearInterpolator == null)
            mLinearInterpolator = new LinearInterpolator();
        return mLinearInterpolator;
    }


    public static class SimpleAnimatorListener implements Animator.AnimatorListener {
        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    }
}
