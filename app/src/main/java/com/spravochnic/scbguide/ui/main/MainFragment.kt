package com.spravochnic.scbguide.ui.main

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.common.base.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentMainBinding
import com.spravochnic.scbguide.ui.common.DialogErrorFragment
import com.spravochnic.scbguide.ui.common.ErrorTransactionEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.system.exitProcess


class MainFragment : BaseFragment() {

    private val viewModel: MainViewModel by viewModels { viewModelFactory }
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMainBinding.inflate(inflater, container, false).apply {
        binding = this
        mainViewModel = viewModel
        lifecycleOwner = viewLifecycleOwner
        setStatusBarColor(StatusBarColor.DARK_YELLOW.color)
    }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservable()
    }

    private fun setObservable() {
        lifecycleScope.launch {
            viewModel.mainUIState.collect { state ->
                when (state) {
                    is MainState.Empty -> {
                        viewModel.loadData()
                    }
                    is MainState.LoadingMainState -> {
                        viewModel.isLoading = true
                    }
                    is MainState.SuccessMainState -> {
                        viewModel.isLoading = false
                    }
                    is MainState.ErrorMainState -> {
                        showSnackBar(binding.root, buttonText = "Загрузить") {
                            viewModel.updateDate()
                        }
                    }
                    is MainState.FailureMainState -> {
                        showSnackBar(binding.root, buttonText = "Загрузить") {
                            viewModel.updateDate()
                        }
                    }
                    is MainState.ErrorLectureMainState -> {
                        updateLoadData(state.errorMessage)
                    }
                    is MainState.ErrorCategoriesLectureMainState -> {
                        updateLoadData(state.errorMessage)
                    }
                    is MainState.ErrorTestMainState -> {
                        updateLoadData(state.errorMessage)
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.transitionEvent.collect { event ->
                when (event) {
                    TransactionEvent.LECTURE -> {
                        findNavController().navigate(MainFragmentDirections.actionMainFragmentToLectureFragment())

                    }
                    TransactionEvent.TEST -> {
                        findNavController().navigate(MainFragmentDirections.actionMainFragmentToTestFragment())
                    }
                }
            }
        }
    }

    private fun updateLoadData(
        errorMessage: String
    ) {
        showSnackBar(binding.root, textSnackBar = errorMessage, buttonText = "Загрузить снова") {
            viewModel.updateDate()
        }
    }
}