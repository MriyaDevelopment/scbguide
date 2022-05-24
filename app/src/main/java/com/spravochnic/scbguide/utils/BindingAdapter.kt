package com.spravochnic.scbguide.utils

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("android:visibility")
fun setVisibility(view: View, visible: Boolean) {
    if (visible) {
        view.visible()
        view.animate().alpha(1f)
    } else {
        view.animate().alpha(0f)
        view.gone()
    }
}