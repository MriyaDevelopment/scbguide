package com.spravochnic.scbguide.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.*
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.spravochnic.scbguide.App
import com.spravochnic.scbguide.R
import javax.inject.Inject

class ContextUtils @Inject constructor(
    val context: Context,
    val instance: App
) {

    fun getString(@StringRes id: Int, vararg args: Any): String {
        return instance.getString(id, *args)
    }

    @ColorInt
    fun getColor(context: Context, @ColorRes resId: Int): Int {
        return ContextCompat.getColor(context, resId)
    }

    fun getDrawable(
        context: Context,
        @DrawableRes resId: Int,
    ): Drawable? {
        return ContextCompat.getDrawable(context, resId)
    }
}