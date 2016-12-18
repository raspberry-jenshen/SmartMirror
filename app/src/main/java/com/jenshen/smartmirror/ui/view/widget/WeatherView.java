package com.jenshen.smartmirror.ui.view.widget;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.jenshen.smartmirror.data.entity.widget.info.WeatherWidgetData;

public class WeatherView extends View implements Widget<WeatherWidgetData> {
    public WeatherView(Context context) {
        super(context);
    }

    public WeatherView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WeatherView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WeatherView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void updateWidget(WeatherWidgetData weatherWidgetData) {

    }
}
