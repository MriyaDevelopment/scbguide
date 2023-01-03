package com.spravochnic.scbguide.ui.auth.register

import androidx.fragment.app.viewModels
import com.spravochnic.scbguide.base.ui.fragments.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel by viewModels<RegisterViewModel>()

    override fun setListeners() = with(binding) {
        tbRegister.setNavigationOnClickListener {
            onBackPressed()
        }

        btnLoginRegister.setOnClickListener {
            onBackPressed()
        }
    }
}