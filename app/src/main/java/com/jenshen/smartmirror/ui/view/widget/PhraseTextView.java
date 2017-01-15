package com.jenshen.smartmirror.ui.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.jenshen.smartmirror.data.entity.widget.info.PhraseWidgetData;
import com.jenshen.smartmirror.ui.view.ScaledTextView;


public class PhraseTextView extends ScaledTextView implements Widget<PhraseWidgetData> {
    public PhraseTextView(Context context) {
        super(context);
    }

    public PhraseTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PhraseTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void updateWidget(PhraseWidgetData phraseWidgetData) {

    }
}
