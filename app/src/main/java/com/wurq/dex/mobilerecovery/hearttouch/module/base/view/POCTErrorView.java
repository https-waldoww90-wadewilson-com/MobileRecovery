package com.wurq.dex.mobilerecovery.hearttouch.module.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wurq.dex.mobilerecovery.R;

/**
 *
 **/
public class POCTErrorView extends FrameLayout {
    private View errorView;

    public POCTErrorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(attrs);
    }

    public POCTErrorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public POCTErrorView(Context context) {
        this(context, null);
    }

    public static POCTErrorView getYXErrorView(Context context) {
        POCTErrorView POCTErrorView = new POCTErrorView(context);
        POCTErrorView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return POCTErrorView;
    }

    private void initView(AttributeSet attrs) {
        inflate(getContext(), R.layout.view_common_error, this);
        setReloadClickListener(null);
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = getContext()
                .obtainStyledAttributes(attrs, R.styleable.POCTErrorView);
        Drawable errorIconDrawble = typedArray.getDrawable(R.styleable.POCTErrorView_errorIconDrawable);
        String errorHint = typedArray.getString(R.styleable.POCTErrorView_errorHint);
        typedArray.recycle(); //不要忘了回收资源
        if (errorIconDrawble != null) {
            setBlankIconDrawable(errorIconDrawble);
        }
        if (errorHint != null) {
            setBlankHint(errorHint);
        }
    }

    public void setBlankIconDrawable(Drawable errorIconDrawable) {
        ((ImageView) findViewById(R.id.error_icon)).setImageDrawable(errorIconDrawable);
    }

    public void setBlankHint(String errorHint) {
        ((TextView) findViewById(R.id.error_text)).setText(errorHint);
    }

    public void setReloadClickListener(OnClickListener listener) {
        if (errorView == null) {
            errorView = findViewById(R.id.error_view);
        }
        errorView.setOnClickListener(listener);
    }

}
