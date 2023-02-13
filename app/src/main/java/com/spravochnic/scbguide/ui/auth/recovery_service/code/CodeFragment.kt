package com.spravochnic.scbguide.ui.auth.recovery_service.code

import android.annotation.SuppressLint
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputEditText
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.base.ui.fragments.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentCodeBinding
import com.spravochnic.scbguide.ui.auth.recovery_service.RecoverServiceViewModel
import com.spravochnic.scbguide.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CodeFragment: BaseFragment<FragmentCodeBinding>(FragmentCodeBinding::inflate) {

    private val viewModel by viewModels<CodeViewModel>()
    private val recoverServiceViewModel by viewModels<RecoverServiceViewModel>({ requireParentFragment() })
    override fun initOnBackPressedDispatcher() = Unit

    @SuppressLint("ClickableViewAccessibility")
    override fun setListeners() = with(binding) {
        incCode.run {
            txtFirstCode.run {
                doAfterTextChanged {
                    if (it?.length == LENGTH_ONE) {
                        onEditorAction(EditorInfo.IME_ACTION_NEXT)
                    }
                    viewModel.onChangeFirstCode(it.toString())
                }
                setOnKeyListener { v, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        txtFirstCode.setText("")
                    }
                    false
                }
            }

            txtSecondCode.run {
                doAfterTextChanged {
                    if (it.toString().length == LENGTH_ONE) {
                        onEditorAction(EditorInfo.IME_ACTION_NEXT)
                    } else if (it?.length == LENGTH_ZERO) {
                        onEditorAction(EditorInfo.IME_ACTION_PREVIOUS)
                    }
                    viewModel.onChangeSecondCode(it.toString())
                }
                setOnKeyListener { v, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        txtSecondCode.setText("")
                    }
                    false
                }
            }

            txtThirdCode.run {
                doAfterTextChanged {
                    if (it?.length == LENGTH_ONE) {
                        onEditorAction(EditorInfo.IME_ACTION_NEXT)
                    } else if (it?.length == LENGTH_ZERO) {
                        onEditorAction(EditorInfo.IME_ACTION_PREVIOUS)
                    }
                    viewModel.onChangeThirdCode(it.toString())
                }
                setOnKeyListener { v, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        txtThirdCode.setText("")
                    }
                    false
                }
            }

            txtFourthCode.run {
                doAfterTextChanged {
                    if (it?.length == LENGTH_ZERO) {
                        onEditorAction(EditorInfo.IME_ACTION_PREVIOUS)
                    }
                    viewModel.onChangeFourthCode(it.toString())
                }
                inputActionDone(btnCode.isEnabled) { onClickCode() }
                setOnKeyListener { v, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        txtFourthCode.setText("")
                    }
                    false
                }
            }
        }

        btnTimerCode.setOnClickListener {
            recoverServiceViewModel.onClickCodeRequest()
        }

        btnCode.setOnThrottleClickListener {
            onClickCode()
        }
    }

    override fun setObservable() = with(viewModel) {
        validator.observe(viewLifecycleOwner) {
            binding.btnCode.isEnabled = it
        }

        timerFlow.observe(viewLifecycleOwner) { timerResult ->
            when(timerResult) {
                is CodeViewModel.TimerResult.DropTimer -> {
                    binding.btnTimerCode.run {
                        setTextColor(getColor(R.color.system_blue_04))
                        text = timerResult.result
                    }
                }
                is CodeViewModel.TimerResult.StartTimer -> {
                    binding.btnTimerCode.run {
                        setTextColor(getColor(R.color.system_blue_01))
                        text = timerResult.result
                    }
                }
            }
        }

        recoverServiceViewModel.startTimerFlow.observe(viewLifecycleOwner) {
            viewModel.startTimer()
        }
    }

    private fun onClickCode() {
        recoverServiceViewModel.onClickCode(viewModel.code)
    }

    companion object {
        private const val LENGTH_ONE = 1
        private const val LENGTH_ZERO = 0
    }
}