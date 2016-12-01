package com.wurq.dex.mobilerecovery.hearttouch.common.view.expandableheightgridview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * 内容全部展开的GridView,不能滚动,也不可以上下滑动
 * Created by ht-template
 **/
public class ExpandableHeightListView extends ListView {

    boolean expanded = false;

    public ExpandableHeightListView(Context context) {
        super(context);
    }

    public ExpandableHeightListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandableHeightListView(Context context, AttributeSet attrs,
                                    int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isExpanded()) {
            // Calculate entire height by providing a very large height hint.
            // View.MEASURED_SIZE_MASK represents the largest height possible.
            int expandSpec = MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK,
                    MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            return true;// 禁止Gridview进行滑动
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 重写的onTouchEvent回调方法
        switch (event.getAction()) {
            // 按下
            case MotionEvent.ACTION_DOWN:
                return super.onTouchEvent(event);
            // 滑动
            case MotionEvent.ACTION_MOVE:
                break;
            // 离开
            case MotionEvent.ACTION_UP:
                return super.onTouchEvent(event);
        }
        // 注意：返回值是false
        return false;
    }

    @SuppressLint({"NewApi", "NewApi"})
    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        // 重写的onTouchEvent回调方法
        switch (event.getAction()) {
            // 按下
            case MotionEvent.ACTION_DOWN:
                return super.onGenericMotionEvent(event);
            // 滑动
            case MotionEvent.ACTION_MOVE:
                break;
            // 离开
            case MotionEvent.ACTION_UP:
                return super.onGenericMotionEvent(event);
        }
        // 注意：返回值是false
        return false;
    }

}
