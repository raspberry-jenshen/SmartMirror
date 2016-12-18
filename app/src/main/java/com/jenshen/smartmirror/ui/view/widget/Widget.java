package com.jenshen.smartmirror.ui.view.widget;

import com.jenshen.smartmirror.data.entity.widget.info.WidgetData;

public interface Widget<Type extends WidgetData> {

    void updateWidget(Type type);

}
