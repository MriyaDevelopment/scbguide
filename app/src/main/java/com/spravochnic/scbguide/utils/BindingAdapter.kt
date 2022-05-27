package com.spravochnic.scbguide.utils

import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.appbar.MaterialToolbar

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

@BindingAdapter("onNavigationIconClick")
fun onNavigationIconClick(materialToolbar: MaterialToolbar, listener: Runnable) {
    materialToolbar.setNavigationOnClickListener {
        listener.run()
    }
}