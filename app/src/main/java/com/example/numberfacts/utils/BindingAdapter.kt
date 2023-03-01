package com.example.numberfacts.utils

import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.numberfacts.R

object NumbersBindingAdapter {

    @JvmStatic
    @BindingAdapter("setNumberValue")
    fun setNumberValue(textView: TextView, value: Long) {
        textView.text = value.toString()
    }

    @JvmStatic
    @BindingAdapter("setSingleLineText")
    fun setSingleLineText(textView: TextView, oneLine: Boolean) {
        textView.isSingleLine = oneLine
        textView.ellipsize = TextUtils.TruncateAt.END
    }

    @JvmStatic
    @BindingAdapter("setMarker")
    fun setMarker(imageView: ImageView, isMarker: Boolean) {
        imageView.setImageResource(
            if (isMarker) {
                R.drawable.marker
            } else {
                0
            }
        )
    }

}