package com.example.numberfacts.utils

import android.text.TextUtils
import android.widget.TextView
import androidx.databinding.BindingAdapter

object NumbersBindingAdapter {

    @JvmStatic
    @BindingAdapter("setNumberValue")
    fun setNumberValue(textView: TextView, value: Long) {
        textView.text = value.toString()
    }

    @JvmStatic
    @BindingAdapter("setSingleLineText")
    fun setSingleLineText(textView: TextView, oneLine: Boolean) {
        textView.setSingleLine(oneLine)
        textView.ellipsize = TextUtils.TruncateAt.END
    }

}