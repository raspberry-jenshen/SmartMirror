package com.jenshen.smartmirror.util.reactive

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import io.reactivex.Observable

fun EditText.onTextChanged(): Observable<String> {
    return Observable.create<String> {
        val textChangedListener = object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!it.isDisposed) {
                    it.onNext(s.toString())
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
            }
        }
        addTextChangedListener(textChangedListener)
        it.setCancellable({ this.removeTextChangedListener(textChangedListener) })
    }
}

fun EditText.onEditorAction(): Observable<Int> {
    return Observable.create<Int> {
        val onEditorActionListener = EditText.OnEditorActionListener { v, actionId, event ->
            if (!it.isDisposed) {
                it.onNext(actionId)
                true
            } else {
                false
            }
        }
        setOnEditorActionListener(onEditorActionListener)
    }
}

