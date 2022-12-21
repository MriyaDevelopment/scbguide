package com.spravochnic.scbguide.ui.main

import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.base.ui.fragments.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment: BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel by viewModels<MainContentViewModel>()

    override fun onBackPressed() {
        navViewModel.onClickHardDeepLink("".toUri(), R.id.main_nav_graph)
    }
}