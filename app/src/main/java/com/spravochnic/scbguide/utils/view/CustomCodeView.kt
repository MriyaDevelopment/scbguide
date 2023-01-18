package com.spravochnic.scbguide.utils.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import com.spravochnic.scbguide.databinding.ViewCustomCodeBinding
import com.spravochnic.scbguide.utils.Constants
import com.spravochnic.scbguide.utils.LogUtil

class CustomCodeView: FrameLayout {

    private val binding =
        ViewCustomCodeBinding.inflate(LayoutInflater.from(context), this, true)

    private var textFirst = ""
    private var textSecond = ""
    private var textThird = ""
    private var textFourth = ""

    override fun onSaveInstanceState(): Parcelable {
        return bundleOf(
            Constants.SUPER_STATE to super.onSaveInstanceState(),
            FIRST_NUMBER to textFirst,
            SECOND_NUMBER to textSecond,
            THIRD_NUMBER to textThird,
            FOURTH_NUMBER to textFourth
        )
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var restoreState = state
        if (restoreState is Bundle) {
            val bundle = restoreState
            textFirst = bundle.getString(FIRST_NUMBER) ?: ""
            textSecond = bundle.getString(SECOND_NUMBER) ?: ""
            textThird = bundle.getString(THIRD_NUMBER) ?: ""
            textFourth = bundle.getString(FOURTH_NUMBER) ?: ""
            restoreState = bundle.getParcelable(Constants.SUPER_STATE)
        }
        super.onRestoreInstanceState(restoreState)
    }

    private var onChangeCodeListener: ((String) -> Unit)? = null

    fun setOnChangeCodeListener(action: (String) -> Unit) {
        onChangeCodeListener = action
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setListeners()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners() {
        with(binding) {
            txtFirstCode.addTextChangedListener {
                LogUtil.d("TAGATGATG "+it.toString())
            }
            txtFirstCode.run {
                doAfterTextChanged {
                    LogUtil.d("TAGATG "+it.toString())
                    textFirst = it.toString()
                    if (it?.length == 1) {

                        onEditorAction(EditorInfo.IME_ACTION_NEXT)
                        txtSecondCode.requestFocus()
                    }
                }
                setOnTouchListener { v, event ->
                    onTouchEvent(event)
                    setSelection(text.toString().length)
                    true
                }
            }

            txtSecondCode.run {
                doAfterTextChanged {
                    textSecond = it.toString()
                    if (it.toString().length == 1) {
                        onEditorAction(EditorInfo.IME_ACTION_NEXT)
                        txtThirdCode.requestFocus()
                    }
                }
                setOnTouchListener { v, event ->
                    onTouchEvent(event)
                    setSelection(text.toString().length)
                    true
                }
            }

            txtThirdCode.run {
                doAfterTextChanged {
                    textThird = it.toString()
                    if (it.toString().length == 1) {
                        onEditorAction(EditorInfo.IME_ACTION_NEXT)
                        txtFourthCode.requestFocus()
                    }
                }
                setOnTouchListener { v, event ->
                    onTouchEvent(event)
                    setSelection(text.toString().length)
                    true
                }
            }

            txtFourthCode.run {
                doAfterTextChanged {
                    textFourth = it.toString()
                    setOnEditorActionListener { v, actionId, event ->
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            onChangeCodeListener?.invoke(textFirst+textSecond+textThird+textFourth)
                            true
                        } else {
                            false
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val FIRST_NUMBER = "FIRST_NUMBER"
        private const val SECOND_NUMBER = "SECOND_NUMBER"
        private const val THIRD_NUMBER = "THIRD_NUMBER"
        private const val FOURTH_NUMBER = "FOURTH_NUMBER"
    }
}