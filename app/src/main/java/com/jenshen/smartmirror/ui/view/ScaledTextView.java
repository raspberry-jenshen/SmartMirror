package com.jenshen.smartmirror.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;

public class ScaledTextView extends AppCompatTextView {
    public ScaledTextView(Context context) {
        super(context);
    }

    public ScaledTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaledTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        setTextSize(TypedValue.COMPLEX_UNIT_PX, measuredWidth / 2.5f);
    }
}
