package com.wurq.dex.mobilerecovery.hearttouch.common.view.bannerindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wurq.dex.mobilerecovery.hearttouch.common.util.ScreenUtil;
import com.wurq.dex.mobilerecovery.R;

/**
 * banner图片指示器
 * Created by ht-template
 **/
public class BannerIndicatorLayout extends LinearLayout {
    /**
     * 默认指示器的大小
     */
    private final static int defaultSize = ScreenUtil.dip2px(7);
    Context mContext;
    ImageView[] mIndicators;
    /**
     * 选中的背景(颜色和尺寸)
     */
    private int selcetBackground;
    /**
     * 未选中的背景(颜色和尺寸)
     */
    private int unselcetBackground;
    /**
     * 指示器的间距
     */
    private int space;
    /**
     * 被选中指示器的大小
     */
    private int selectIndicatorSize;
    /**
     * 未被选中指示器的大小
     */
    private int unselectIndicatorSize;


    public BannerIndicatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BannerIndicatorLayout);

        try {
            selcetBackground = typedArray.getResourceId(R.styleable.BannerIndicatorLayout_select_background, R.drawable.shape_index_page_banner_indicator_selected);
            unselcetBackground = typedArray.getResourceId(R.styleable.BannerIndicatorLayout_unselect_background, R.drawable.shape_index_page_banner_indicator_unselected);
            selectIndicatorSize = typedArray.getDimensionPixelSize(R.styleable.BannerIndicatorLayout_select_size, defaultSize);
            unselectIndicatorSize = typedArray.getDimensionPixelSize(R.styleable.BannerIndicatorLayout_unselect_size, defaultSize);
            space = typedArray.getDimensionPixelSize(R.styleable.BannerIndicatorLayout_indicator_space, 10);

        } finally {
            typedArray.recycle();
        }
    }

    public void initIndicators(int num, int position) {
        if (num <= 1) {
            setVisibility(GONE);
            return;
        } else {
            setVisibility(VISIBLE);
        }

        position = position % num;
        if (mIndicators == null || mIndicators.length != num) {
            removeAllViews();
            mIndicators = new ImageView[num];

            for (int i = 0; i < num; i++) {
                ImageView indicator = new ImageView(mContext);
                LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(space / 2, 0, space / 2, 0);
                lp.gravity = Gravity.CENTER_VERTICAL;
                indicator.setLayoutParams(lp);

                addView(indicator);

                mIndicators[i] = indicator;
            }
        }

        changeIndicator(position);

        invalidate();
    }

    public void changeIndicator(int position) {
        for (int i = 0; i < mIndicators.length; i++) {
            ViewGroup.LayoutParams lp = mIndicators[i].getLayoutParams();
            if (i == position) {
                lp.height = lp.width = selectIndicatorSize;
                mIndicators[i].setBackgroundResource(selcetBackground);
            } else {
                lp.height = lp.width = unselectIndicatorSize;
                mIndicators[i].setBackgroundResource(unselcetBackground);
            }
            mIndicators[i].setLayoutParams(lp);
        }
    }
}
