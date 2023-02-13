package com.spravochnic.scbguide.ui.profile

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.base.ui.fragments.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentProfileBinding
import com.spravochnic.scbguide.ui.profile.adapter.ProfileAdapter
import com.spravochnic.scbguide.utils.dp
import com.spravochnic.scbguide.utils.enums.ProfileType
import com.spravochnic.scbguide.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel by viewModels<ProfileViewModel>()

    @Inject
    lateinit var adapter: ProfileAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onBackPressed() {
        navViewModel.onClickHardDeepLink("".toUri(), R.id.main_nav_graph)
    }

    override fun setAdapter() = with(binding) {
        rvProfile.itemAnimator = null
        rvProfile.isNestedScrollingEnabled = false
        rvProfile.adapter = adapter
    }

    override fun setListeners() = with(binding) {
        adapter.setOnClickProfileItemListener { type ->

        }
    }

    override fun setObservable() = with(viewModel) {
        profileFlow.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
    }
}