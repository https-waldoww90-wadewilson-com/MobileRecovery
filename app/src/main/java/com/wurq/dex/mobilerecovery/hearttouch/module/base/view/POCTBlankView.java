package com.wurq.dex.mobilerecovery.hearttouch.module.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wurq.dex.mobilerecovery.R;

/**
 * Created by ht-template
 **/
public class POCTBlankView extends FrameLayout {
    private View blankView;

    public POCTBlankView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(attrs);
    }

    public POCTBlankView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public POCTBlankView(Context context) {
        this(context, null);
    }

    public static POCTBlankView getYXBlankView(Context context) {
        POCTBlankView POCTBlankView = new POCTBlankView(context);
        POCTBlankView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return POCTBlankView;
    }

    private void initView(AttributeSet attrs) {
        inflate(getContext(), R.layout.view_common_blank, this);
        blankView = findViewById(R.id.blank_view);
        blankView.setOnClickListener(null);
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = getContext()
                .obtainStyledAttributes(attrs, R.styleable.POCTBlankView);
        Drawable blankIconDrawble = typedArray.getDrawable(R.styleable.POCTBlankView_blankIconDrawable);
        String blankHint = typedArray.getString(R.styleable.POCTBlankView_blankHint);
        typedArray.recycle(); //不要忘了回收资源
        if (blankIconDrawble != null) {
            setBlankIconDrawable(blankIconDrawble);
        }
        if (blankHint != null) {
            setBlankHint(blankHint);
        }
    }

    public void setBlankIconDrawable(Drawable blankIconDrawable) {
//        ((ImageView) findViewById(R.id.blank_icon)).setImageDrawable(blankIconDrawable);
    }

    public void setBlankHint(String blankHint) {
        ((TextView) findViewById(R.id.blank_text)).setText(blankHint);
    }
}
