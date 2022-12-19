package com.spravochnic.scbguide.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import coil.load
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.spravochnic.scbguide.R

fun ImageView.loadImageWithShimmer(url: Any? = null) {
    this.load(url) {
        placeholder(this@loadImageWithShimmer.context.getDefaultShimmer())
    }
}

fun Context.getDefaultShimmer(): Drawable {
    val shimmer = Shimmer.ColorHighlightBuilder().setBaseColor(
        ContextCompat.getColor(this, R.color.backgroundDefaultShimmer)
    ).setHighlightColor(
        ContextCompat.getColor(this, R.color.shimmer)
    ).setDuration(1000)
        .setBaseAlpha(1f)
        .setHighlightAlpha(1f)
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
        .setAutoStart(true)
        .build()
    return ShimmerDrawable().apply {
        setShimmer(shimmer)
    }
}