package com.spravochnic.scbguide.utils

import android.animation.ValueAnimator
import android.content.Context
import android.util.Size
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.view.*
import androidx.viewbinding.ViewBinding
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.textfield.TextInputEditText

fun ViewBinding.getString(@StringRes id: Int, vararg formatArgs: Any?) = root.resources.getString(id, *formatArgs)
fun ViewBinding.getColor(@ColorRes id: Int) = ContextCompat.getColor(root.context, id)
fun ViewBinding.getDrawable(@DrawableRes id: Int) = ContextCompat.getDrawable(root.context, id)
fun ViewBinding.getDimen(@DimenRes id: Int) = this.root.resources.getDimension(id)
fun ViewBinding.getColorStateList(@ColorRes id: Int) = ContextCompat.getColorStateList(root.context, id)

fun View.getColorStateList(@ColorRes id: Int) = ContextCompat.getColorStateList(context, id)
fun View.getDimen(@DimenRes id: Int) = resources.getDimension(id)
fun View.getColor(@ColorRes id: Int) = ContextCompat.getColor(context, id)

fun View.updateMargin(
    @Px left: Int = marginStart,
    @Px top: Int = marginTop,
    @Px right: Int = marginEnd,
    @Px bottom: Int = marginBottom
){
    updateLayoutParams<ViewGroup.MarginLayoutParams> {
        marginStart = left
        topMargin = top
        marginEnd = right
        bottomMargin = bottom
    }
}

fun MaterialCardView.setCornerRadius(topLeft: Float, topRight: Float, bottomLeft: Float, bottomRight: Float) {
    val builderShapeModel = this.shapeAppearanceModel.toBuilder().apply {
        setTopLeftCorner(CornerFamily.ROUNDED, topLeft)
        setTopRightCorner(CornerFamily.ROUNDED, topRight)
        setBottomLeftCorner(CornerFamily.ROUNDED, bottomLeft)
        setBottomRightCorner(CornerFamily.ROUNDED, bottomRight)
    }
    this.shapeAppearanceModel = builderShapeModel.build()
}

fun View.measureSize(): Size {
    this.measure(
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    )
    return Size(this.measuredWidth, this.measuredHeight)
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.slideUp(duration: Int = 500, plus: Int) {
    val valueAnimator = ValueAnimator.ofInt(0, plus)
    valueAnimator.duration = duration.toLong()
    valueAnimator.addUpdateListener {
        val animatedValue = valueAnimator.animatedValue as Int
        if (animatedValue == 0 && plus != 0) return@addUpdateListener
        val layoutParams = this.layoutParams
        layoutParams.height = animatedValue
        this.layoutParams = layoutParams
    }
    valueAnimator.start()

}

fun View.slideDown(duration: Int = 500, minus: Int) {
    val valueAnimator = ValueAnimator.ofInt(minus, 0)
    valueAnimator.duration = duration.toLong()
    valueAnimator.addUpdateListener {
        val animatedValue = valueAnimator.animatedValue as Int
        if (animatedValue == 0) return@addUpdateListener
        val layoutParams = this.layoutParams
        layoutParams.height = animatedValue
        this.layoutParams = layoutParams
    }
    valueAnimator.start()
}

fun View.showSoftKeyboard() {
    if (requestFocus()) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun View.hideSoftKeyboard() {
    val imm =
        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun TextInputEditText.inputActionDone(isEnabledBtn: Boolean, action: () -> Unit) {
    this.setOnEditorActionListener { _, actionId, _ ->
        if (actionId != EditorInfo.IME_ACTION_DONE)
            return@setOnEditorActionListener false

        if (isEnabledBtn) {
            action.invoke()
        } else {
            this.hideSoftKeyboard()
        }

        return@setOnEditorActionListener true
    }
}