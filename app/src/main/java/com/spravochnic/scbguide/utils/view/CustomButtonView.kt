package com.spravochnic.scbguide.utils.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color.blue
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.os.bundleOf
import androidx.core.view.doOnLayout
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.databinding.ViewCustomButtonBinding
import com.spravochnic.scbguide.utils.*
import com.spravochnic.scbguide.utils.ClickUtils.setOnThrottleClickListener

class CustomButtonView : FrameLayout {

    private val binding =
        ViewCustomButtonBinding.inflate(LayoutInflater.from(context), this, true)

    private var progressWidth: Int = 0
    private var btnWidth: Int = 0
    private var normalCorners = 10.dp
    private var loadingCorners = 50.dp
    private val doneDrawable = R.drawable.ic_done
    private val errorDrawable = R.drawable.ic_cancel
    private val blueColorStateList = getColorStateList(R.color.background_button_enabled)
    private val redColorStateList = getColorStateList(R.color.background_button_enabled_red)
    private val transparentStateList = getColorStateList(R.color.transparent)
    private val systemRedLightColorStateList = getColorStateList(R.color.system_red_01)
    private val whiteColorStateList = getColorStateList(R.color.white)
    private val whiteColor = getColor(R.color.white)
    private val redColor = getColor(R.color.system_red_01)
    private val strokeWidthOutlineRedButton = 2.dp

    var type: Type = Type.blue
        set(value) {
            field = value
            initType()
        }

    var state: State? = null
        set(value) {
            field = value
            resizeByState()
        }

    var text: String = ""
        set(value) {
            field = value
            initText()
        }

    fun <T> setUIState(uiState: UIState<T>) {
        when (uiState) {
            is UIState.UILoading -> state = State.LOADING
            is UIState.UIError -> state = State.ERROR
            is UIState.UIPreSuccess -> state = State.DONE
            else -> Unit
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        return bundleOf(
            Constants.SUPER_STATE to super.onSaveInstanceState(),
            TYPE to type.name,
            TEXT to text
        )
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var restoreState = state
        if (restoreState is Bundle) {
            val bundle = restoreState
            val buttonType = bundle.getString(TYPE)
            type = Type.values()
                .firstOrNull { it.name == buttonType }
                ?: Type.blue
            text = bundle.getString(TEXT) ?: ""
            restoreState = bundle.getParcelable(Constants.SUPER_STATE)
        }
        super.onRestoreInstanceState(restoreState)
    }

    private var onEndAnimateDoneListener: (() -> Unit)? = null
    private var onEndAnimateErrorListener: (() -> Unit)? = null
    private var onThrottleClickListener: (() -> Unit)? = null

    fun setOnEndAnimateDoneListener(action: () -> Unit) {
        onEndAnimateDoneListener = action
    }

    fun setOnEndAnimateErrorListener(action: () -> Unit) {
        onEndAnimateErrorListener = action
    }

    fun setOnThrottleClickListener(action: () -> Unit) {
        onThrottleClickListener = action
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val a =
            context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.CustomButtonView,
                defStyleAttr,
                0
            )


        val buttonType = a.getInt(R.styleable.CustomButtonView_buttonType, 0)
        type = Type.values()[buttonType]

        text = a.getString(R.styleable.CustomButtonView_android_text) ?: ""

        isEnabled = a.getBoolean(R.styleable.CustomButtonView_android_enabled, true)

        setListeners()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        binding.btnCustom.isEnabled = isEnabled
    }

    private fun setListeners() = with(binding) {
        btnCustom.setOnThrottleClickListener {
            if (state == State.DONE || state == State.ERROR || state == State.LOADING) {
                return@setOnThrottleClickListener
            }
            onThrottleClickListener?.invoke()
        }
    }

    init {
        binding.cpiCustom.doOnLayout {
            progressWidth = it.width
        }
        binding.btnCustom.doOnLayout {
            btnWidth = it.width
        }
    }

    private fun initText() = with(binding) {
        btnCustom.text = text
    }

    private fun resizeByState() {
        when (state) {
            State.NORMAL -> setResizeButton(progressWidth, btnWidth)
            State.LOADING -> setResizeButton(btnWidth, progressWidth)
            State.DONE -> setDoneButton()
            State.ERROR -> setErrorButton()
            else -> Unit
        }
    }

    private fun setErrorButton() = with(binding) {
        ivCustom.setImageResource(errorDrawable)
        cpiCustom.alpha = 0f
        ivCustom.animate().alpha(1f).withEndAction {
            ivCustom.animate().alpha(0f).setDuration(350).withEndAction {
                state = State.NORMAL
                onEndAnimateErrorListener?.invoke()
            }
        }
    }

    private fun setDoneButton() = with(binding) {
        ivCustom.setImageResource(doneDrawable)
        cpiCustom.alpha = 0f
        ivCustom.animate().alpha(1f).withEndAction {
            ivCustom.animate().alpha(0f).setDuration(350).withEndAction {
                state = State.NORMAL
                onEndAnimateDoneListener?.invoke()
            }
        }
    }

    private fun initType() {
        when (type) {
            Type.blue -> setBlueButton()
            Type.red -> setRedButton()
            Type.outline_red -> setOutlineRed()
        }
    }

    private fun setOutlineRed() = with(binding) {
        btnCustom.run {
            backgroundTintList = transparentStateList
            rippleColor = systemRedLightColorStateList
            strokeColor = systemRedLightColorStateList
            strokeWidth = strokeWidthOutlineRedButton.toInt()
            setTextColor(systemRedLightColorStateList)
        }
        ivCustom.imageTintList = systemRedLightColorStateList
        cpiCustom.setIndicatorColor(redColor)
    }

    private fun setRedButton() = with(binding) {
        btnCustom.run {
            backgroundTintList = redColorStateList
            rippleColor = whiteColorStateList
            setTextColor(whiteColorStateList)
        }
        ivCustom.imageTintList = whiteColorStateList
        cpiCustom.setIndicatorColor(whiteColor)
    }

    private fun setBlueButton() = with(binding) {
        btnCustom.run {
            backgroundTintList = blueColorStateList
            rippleColor = whiteColorStateList
            setTextColor(whiteColorStateList)
        }
        ivCustom.imageTintList = whiteColorStateList
        cpiCustom.setIndicatorColor(whiteColor)
    }

    private fun setResizeButton(currentWidth: Int, newWidth: Int) = with(binding) {
        val params = btnCustom.layoutParams
        ValueAnimator.ofInt(currentWidth, newWidth).setDuration(350).apply {
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { animation: ValueAnimator ->
                val value = animation.animatedValue as Int
                params.width = value
                btnCustom.layoutParams = params
                btnCustom.requestLayout()
            }
            doOnStart {
                doOnResizeStart()
            }
            doOnEnd {
                doOnResizeEnd()
            }
            start()
        }
    }

    private fun doOnResizeEnd() = with(binding) {
        when (state) {
            State.NORMAL -> {
                btnCustom.text = text
                state = null
            }
            State.LOADING -> {
                cpiCustom.animate().alpha(1f)
            }
            else -> Unit
        }
    }

    private fun doOnResizeStart() = with(binding) {
        when (state) {
            State.NORMAL -> {
                btnCustom.cornerRadius = normalCorners.toInt()
                cpiCustom.alpha = 0f
            }
            State.LOADING -> {
                btnCustom.text = ""
                btnCustom.cornerRadius = loadingCorners.toInt()
            }
            else -> Unit
        }
    }

    enum class Type {
        blue,
        red,
        outline_red
    }

    enum class State {
        NORMAL,
        LOADING,
        ERROR,
        DONE
    }

    companion object {
        private const val TYPE = "TYPE"
        private const val TEXT = "TEXT"
    }
}