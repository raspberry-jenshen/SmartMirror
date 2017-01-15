package com.jenshen.smartmirror.ui.holder.widgets

import android.content.Context
import android.support.design.widget.TextInputEditText
import android.view.View
import com.jenshen.smartmirror.data.model.widget.WidgetModel
import kotlinx.android.synthetic.main.item_widget_phrase.view.*


class PhraseWidgetHolder(context: Context,
                         view: View) : WidgetHolder(context, view) {

    private val phraseEdit: TextInputEditText

    init {
        phraseEdit = itemView.phraseEdit
    }

    override fun bindInfo(model: WidgetModel, onItemClicked: (WidgetModel) -> Unit) {
        super.bindInfo(model, onItemClicked)

    }
}