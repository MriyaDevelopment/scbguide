package com.spravochnic.scbguide.ui.auth.recovery_service.recover

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.spravochnic.scbguide.base.network.UIState
import com.spravochnic.scbguide.base.ui.fragments.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentRecoverBinding
import com.spravochnic.scbguide.ui.auth.recovery_service.RecoverServiceViewModel
import com.spravochnic.scbguide.utils.hideKeyboard
import com.spravochnic.scbguide.utils.inputActionDone
import com.spravochnic.scbguide.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecoverFragment : BaseFragment<FragmentRecoverBinding>(FragmentRecoverBinding::inflate) {

    private val viewModel by viewModels<RecoverViewModel>()
    private val recoverServiceViewModel by viewModels<RecoverServiceViewModel>({ requireParentFragment() })
    override fun initOnBackPressedDispatcher() = Unit

    override fun setObservable() = with(viewModel) {
        validatorFlow.observe(viewLifecycleOwner) {
            binding.btnRecover.isEnabled = it
        }

        recoverServiceViewModel.recoverState.observe(viewLifecycleOwner) { uiState ->
            binding.btnRecover.setUIState(uiState)
            when (uiState) {
                is UIState.UILoading -> {
                    windowInsetsController.hideKeyboard()
                }
                else -> Unit
            }
        }
    }

    override fun setListeners() = with(binding) {
        txtEmailRecover.run {
            doAfterTextChanged {
                viewModel.onChangeEmail(it.toString())
            }

            txtEmailRecover.inputActionDone(btnRecover.isEnabled) {
                onClickRecover()
            }
        }

        btnRecover.setOnThrottleClickListener {
            onClickRecover()
        }
    }

    private fun onClickRecover() {
        recoverServiceViewModel.onClickEmail(viewModel.emailFlow.value)
    }
}