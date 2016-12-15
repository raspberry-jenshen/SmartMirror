package com.jenshen.smartmirror.ui.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.jenshen.smartmirror.data.entity.widget.info.InfoForClockWidget;

public class ClockWidget extends FrameLayout implements Widget<InfoForClockWidget> {

    public ClockWidget(Context context) {
        super(context);
    }

    public ClockWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ClockWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ClockWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void updateWidget(InfoForClockWidget infoForClockWidget) {

    }
}
