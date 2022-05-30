package com.spravochnic.scbguide.ui.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.spravochnic.scbguide.adapters.TestAdapter
import com.spravochnic.scbguide.common.base.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentTestBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TestFragment : BaseFragment() {

    private val viewModel: TestViewModel by viewModels { viewModelFactory }
    private lateinit var binding: FragmentTestBinding

    private val testAdapter by lazy { TestAdapter(onClickTestItem = onClickTestItem) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentTestBinding.inflate(inflater, container, false).apply {
        binding = this
        testViewModel = viewModel
        lifecycleOwner = viewLifecycleOwner
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        setObservable()
    }

    private fun initAdapter() {
        binding.recyclerView.adapter = testAdapter
    }

    private fun setObservable() {
        with(lifecycleScope) {
            launch {
                viewModel.testUIState.collect { state ->
                    when (state) {
                        is TestState.Empty -> {
                            viewModel.loadTestData()
                        }
                        is TestState.setDataTest -> {
                            testAdapter.submitList(state.data)
                        }
                    }
                }
            }
            launchWhenStarted {
                viewModel.transitionTestEvent.collect { event ->
                    when (event) {
                        TransactionTestEvent.NAVIGATION -> {
                            popBackStackFragment()
                        }
                    }
                }
            }
        }
    }

    private val onClickTestItem: (String) -> Unit = { type ->
        findNavController().navigate(
            TestFragmentDirections.actionTestFragmentToDetailsTestFragment(
                type = type
            )
        )
    }
}