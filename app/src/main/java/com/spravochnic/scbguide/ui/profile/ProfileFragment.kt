package com.spravochnic.scbguide.ui.profile

import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.base.ui.fragments.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel by viewModels<ProfileViewModel>()

    override fun onBackPressed() {
        navViewModel.onClickHardDeepLink("".toUri(), R.id.main_nav_graph)
    }

    override fun setAdapter() = with(binding) {
        rvProfile.itemAnimator = null
        rvProfile.isNestedScrollingEnabled = false
    }
}