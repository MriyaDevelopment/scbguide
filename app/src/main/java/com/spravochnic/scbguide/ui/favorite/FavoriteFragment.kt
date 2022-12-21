package com.spravochnic.scbguide.ui.favorite

import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.base.ui.fragments.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {

    private val viewModel by viewModels<FavoriteViewModel>()

    override fun onBackPressed() {
        navViewModel.onClickHardDeepLink("".toUri(), R.id.main_nav_graph)
    }
}