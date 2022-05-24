package com.spravochnic.scbguide.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.common.base.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentMainBinding
import com.spravochnic.scbguide.ui.common.DialogErrorFragment
import com.spravochnic.scbguide.ui.common.ErrorTransactionEvent
import kotlinx.coroutines.launch

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
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            DialogErrorFragment.RESULT_ERROR_DIALOG
        )?.observe(viewLifecycleOwner) { result ->
            if (result == ErrorTransactionEvent.REPEAT.name) {
                viewModel.loadLectureData()
            } else {
                println(result)
            }
        }
        lifecycleScope.launch {
            viewModel.mainUIState.collect { state ->
                when (state) {
                    is MainState.Empty -> {
                        viewModel.loadLectureData()
                    }
                    is MainState.LoadingMainState -> {
                        viewModel.isLoading = true
                    }
                    is MainState.SuccessMainState -> {
                        viewModel.isLoading = false
                    }
                    is MainState.ErrorMainState -> {
                        state.errorMessage?.let { message ->
                            transitionFromFragmentToFragment(
                                MainFragmentDirections.actionMainFragmentToDialogErrorFragment(
                                    message
                                )
                            )
                        }
                    }
                    is MainState.FailureMainState -> {
                        transitionFromFragmentToFragment(
                            MainFragmentDirections.actionMainFragmentToDialogErrorFragment(
                                contextUtils.getString(R.string.messageFailure)
                            )
                        )
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.transitionEvent.collect { event ->
                when (event) {
                    TransactionEvent.LECTURE -> {
                        transitionFromFragmentToFragment(MainFragmentDirections.actionMainFragmentToLectureFragment())
                    }
                    TransactionEvent.TEST -> {
                        transitionFromFragmentToFragment(MainFragmentDirections.actionMainFragmentToTestFragment())
                    }
                }
            }
        }
    }
}