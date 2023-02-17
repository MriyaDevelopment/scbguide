package com.spravochnic.scbguide.ui.auth.login

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.spravochnic.scbguide.base.ui.fragments.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentLoginBinding
import com.spravochnic.scbguide.ui.auth.register.RegisterFragment
import com.spravochnic.scbguide.utils.navigateSafe
import com.spravochnic.scbguide.utils.observe
import com.spravochnic.scbguide.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel by viewModels<LoginViewModel>()

    override fun setListeners() = with(binding) {
        txtEmailLogin.doAfterTextChanged {
            viewModel.onChangeEmail(it.toString())
        }

        txtPasswordLogin.doAfterTextChanged {
            viewModel.onChangePassword(it.toString())
        }

        btnLogin.setOnThrottleClickListener {
            if(viewModel.onClickLogin())
                navigationController.navigateSafe(LoginFragmentDirections.actionLoginFragmentToBaseFragment())
        }

        btnRecoveryLogin.setOnClickListener {
            navigationController.navigateSafe(LoginFragmentDirections.actionLoginFragmentToRecoverServiceFragment())
        }

        btnRegisterLogin.setOnClickListener {
            navigationController.navigateSafe(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        setFragmentResultListener(RegisterFragment.MESSAGE) { key, bundle ->
            bundle.getString(key)?.let { showSnackBar(it, root) }
        }
    }

    override fun setObservable() = with(viewModel) {
        validatorFlow.observe(viewLifecycleOwner) {
            binding.btnLogin.isEnabled = it
        }


    }
}