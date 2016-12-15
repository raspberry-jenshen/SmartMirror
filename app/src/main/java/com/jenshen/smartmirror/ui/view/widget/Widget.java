package com.jenshen.smartmirror.ui.view.widget;

import com.jenshen.smartmirror.data.entity.widget.info.InfoForWidget;

public interface Widget<Type extends InfoForWidget> {

    void updateWidget(Type type);

}
