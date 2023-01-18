package com.spravochnic.scbguide.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.base.network.UIState
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
        binding.rvMain.updatePadding(0, 30.dp.toInt(), 0, navViewModel.heightBnvTab + 10.dp.toInt())
    }

    override fun setAdapter() = with(binding) {
        rvMain.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        viewModel.onSwipeRefresh()
    }

    override fun setListeners() = with(binding) {
        srlMain.setOnRefreshListener {
            viewModel.onSwipeRefresh()
        }
    }

    override fun setObservable() = with(viewModel) {
        mainFlow.observeLatest(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UIState.UINone -> binding.srlMain.isRefreshing = false
                is UIState.UILoading -> {
                    setPreLoader(false)
                    binding.srlMain.isRefreshing = true
                }
                is UIState.UISuccess -> {
                    adapter.submitList(uiState.data)
                    setSuccess()
                }
                is UIState.UIError -> setError(
                    uiState.errorMessage,
                    isRootInvisible = false
                ) { viewModel.onSwipeRefresh() }
                else -> Unit
            }
        }
    }

    override fun onBackPressed() {
        requireActivity().finishAndRemoveTask()
    }
}