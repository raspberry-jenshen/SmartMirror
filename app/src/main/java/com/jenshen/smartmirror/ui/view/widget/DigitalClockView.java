package com.jenshen.smartmirror.ui.view.widget;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.TextClock;

import com.jenshen.smartmirror.R;
import com.jenshen.smartmirror.data.entity.widget.info.ClockWidgetData;
import com.jenshen.smartmirror.util.DateUtil;

import static android.view.Gravity.CENTER;

public class DigitalClockView extends TextClock implements Widget<ClockWidgetData> {

    public DigitalClockView(Context context) {
        super(context);
        init();
    }

    public DigitalClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DigitalClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public DigitalClockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    public void updateWidget(ClockWidgetData clockWidgetData) {

    }

    private void init() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setGravity(CENTER);
        setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.textSize_large4));
        setFormat24Hour(DateUtil.Companion.getHOURS_MINUTES_SECONDS());
    }
}
