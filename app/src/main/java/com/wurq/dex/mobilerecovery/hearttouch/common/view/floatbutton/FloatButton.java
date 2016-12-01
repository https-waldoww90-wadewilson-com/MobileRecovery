package com.wurq.dex.mobilerecovery.hearttouch.common.view.floatbutton;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.wurq.dex.mobilerecovery.hearttouch.common.util.AnimationUtil;
import com.wurq.dex.mobilerecovery.hearttouch.common.util.ResourcesUtil;
import com.wurq.dex.mobilerecovery.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * Created by ht-template
 **/
public class FloatButton extends ImageView {
    private float floatButtonLeftPosition;
    private float floatButtonRightPosition;
    private ViewPropertyAnimator animator;

    public FloatButton(Context context) {
        super(context);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams((int) ResourcesUtil.getDimen(R.dimen.float_btn_size),
                (int) ResourcesUtil.getDimen(R.dimen.float_btn_size), Gravity.RIGHT | Gravity.BOTTOM);
        lp.setMargins(0, 0,
                (int) ResourcesUtil.getDimen(R.dimen.float_btn_margin_right),
                (int) ResourcesUtil.getDimen(R.dimen.float_btn_margin_bottom));
        setLayoutParams(lp);
        setClickable(true);
        setFocusable(true);
        setBackgroundResource(R.drawable.selector_btn_black);
//        setCardBackgroundColor(ResourcesUtil.getColor(R.color.green_normal));
//        setRadius((float) ScreenUtil.dip2px(20));
        setTag(FloatButtonState.RIGHT);
        floatButtonLeftPosition = getX();
        floatButtonRightPosition = floatButtonLeftPosition + lp.width * 2;
        setTranslationX(floatButtonRightPosition);
    }

    public synchronized void showFloatButton(boolean isShow) {
        Float position = null;
        if (isShow) {
            if (getTag().equals(FloatButtonState.MOVING_RIGHT) ||
                    getTag().equals(FloatButtonState.RIGHT)) {
                if (animator != null) {
                    animator.cancel();
                    animator = null;
                }
                position = floatButtonLeftPosition;
                setTag(FloatButtonState.MOVING_LEFT);
            }
        } else {
            if (getTag().equals(FloatButtonState.MOVING_LEFT) ||
                    getTag().equals(FloatButtonState.LEFT)) {
                if (animator != null) {
                    animator.cancel();
                    animator = null;
                }
                position = floatButtonRightPosition;
                setTag(FloatButtonState.MOVING_RIGHT);
            }
        }

        if (position != null) {
            animator = ViewPropertyAnimator.animate(this)
                    .translationX(position)
                    .setInterpolator(AnimationUtil.getAccelerateDecelerateInterpolator())
                    .setListener(new AnimationUtil.SimpleAnimatorListener() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (getTag().equals(FloatButtonState.MOVING_LEFT)) {
                                setTag(FloatButtonState.LEFT);
                            } else if (getTag().equals(FloatButtonState.MOVING_RIGHT)) {
                                setTag(FloatButtonState.RIGHT);
                            }
                        }
                    });

            animator.start();
        }
    }

    private interface FloatButtonState {
        int LEFT = 0;
        int RIGHT = 1;
        int MOVING_RIGHT = 2;
        int MOVING_LEFT = 3;
    }

}
