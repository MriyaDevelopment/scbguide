package com.spravochnic.scbguide.ui.auth.recovery_service.password

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.spravochnic.scbguide.base.ui.fragments.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentPasswordBinding
import com.spravochnic.scbguide.ui.auth.recovery_service.RecoverServiceViewModel
import com.spravochnic.scbguide.utils.inputActionDone
import com.spravochnic.scbguide.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordFragment : BaseFragment<FragmentPasswordBinding>(FragmentPasswordBinding::inflate) {

    private val viewModel by viewModels<PasswordViewModel>()
    private val recoverServiceViewModel by viewModels<RecoverServiceViewModel>({ requireParentFragment() })
    override fun initOnBackPressedDispatcher() = Unit

    override fun setListeners() = with(binding) {
        txtRepeatPassword.inputActionDone(btnPassword.isEnabled) {
            onClickChangePass()
        }

        txtPassword.doAfterTextChanged {
            viewModel.onChangePassword(it.toString())
        }

        txtRepeatPassword.doAfterTextChanged {
            viewModel.onChangeRepeatPassword(it.toString())
        }

        btnPassword.setOnThrottleClickListener {
            onClickChangePass()
        }
    }

    override fun setObservable() = with(viewModel) {
        validateFlow.observe(viewLifecycleOwner) {
            binding.btnPassword.isEnabled = it
        }

        recoverServiceViewModel.changePassState.observe(viewLifecycleOwner) { uiState ->
            binding.btnPassword.setUIState(uiState)
        }
    }

    private fun onClickChangePass() {
        recoverServiceViewModel.onClickChangePass(viewModel.passwordFlow.value)
    }
}