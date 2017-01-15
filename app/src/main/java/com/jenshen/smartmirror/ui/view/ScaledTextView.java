package com.jenshen.smartmirror.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

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
}
