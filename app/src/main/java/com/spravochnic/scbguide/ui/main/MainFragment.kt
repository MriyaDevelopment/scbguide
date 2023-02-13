package com.spravochnic.scbguide.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import com.spravochnic.scbguide.base.ui.fragments.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentMainBinding
import com.spravochnic.scbguide.ui.main.adapter.MainContentAdapter
import com.spravochnic.scbguide.utils.dp
import com.spravochnic.scbguide.utils.observe
import com.spravochnic.scbguide.utils.observeLatest
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel by viewModels<MainContentViewModel>()

    @Inject
    lateinit var adapter: MainContentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setAdapter() = with(binding) {
        rvMain.adapter = adapter
    }

    override fun setListeners() = with(binding) {
        srlMain.setOnRefreshListener {
            onSwipeRefresh()
        }
    }

    override fun setObservable() = with(viewModel) {

    }

    private fun onSwipeRefresh() {
        viewModel.onSwipeRefresh()
    }

    override fun onBackPressed() {
        requireActivity().finishAndRemoveTask()
    }
}