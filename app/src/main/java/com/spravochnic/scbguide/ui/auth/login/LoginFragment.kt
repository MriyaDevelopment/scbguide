package com.spravochnic.scbguide.ui.auth.login

import androidx.fragment.app.viewModels
import com.spravochnic.scbguide.base.ui.fragments.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentLoginBinding
import com.spravochnic.scbguide.utils.navigateSafe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel by viewModels<LoginViewModel>()

    override fun setListeners() = with(binding) {
        btnRegisterLogin.setOnClickListener {
            navigationController.navigateSafe(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }
}