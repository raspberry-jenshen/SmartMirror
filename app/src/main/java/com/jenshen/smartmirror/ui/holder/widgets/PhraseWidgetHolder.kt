package com.jenshen.smartmirror.ui.holder.widgets

import android.content.Context
import android.support.design.widget.TextInputEditText
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import com.jenshen.smartmirror.data.model.widget.WidgetModel
import kotlinx.android.synthetic.main.item_widget_phrase.view.*


class PhraseWidgetHolder(context: Context,
                         view: View) : WidgetHolder(context, view) {

    private val phraseEdit: TextInputEditText
    private val okButton: Button

    init {
        okButton = itemView.ok
        phraseEdit = itemView.phraseEdit
        phraseEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                okButton.isEnabled = s.isNotEmpty()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }
        })
    }

    override fun bindInfo(model: WidgetModel, onItemClicked: (WidgetModel) -> Unit) {
        super.bindInfo(model, onItemClicked)
        okButton.setOnClickListener {
            model.phrase = phraseEdit.text.toString()
            onItemClicked(model) }
    }
}