package com.spravochnic.scbguide.ui.quiz

import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.base.network.UIState
import com.spravochnic.scbguide.base.ui.fragments.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentQuizBinding
import com.spravochnic.scbguide.ui.quiz.adapter.QuizAdapter
import com.spravochnic.scbguide.utils.observe
import com.spravochnic.scbguide.utils.observeLatest
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class QuizFragment : BaseFragment<FragmentQuizBinding>(FragmentQuizBinding::inflate) {

    private val viewModel by viewModels<QuizViewModel>()

    @Inject
    lateinit var adapter: QuizAdapter

    override fun onBackPressed() {
        navViewModel.onClickHardDeepLink("".toUri(), R.id.main_nav_graph)
    }

    override fun setAdapter() {
        binding.rvQuiz.adapter = adapter
    }

    override fun setListeners() = with(binding) {
        srlQuiz.setOnRefreshListener {
            onSwipeRefresh()
        }
    }

    override fun setObservable() = with(viewModel) {
        quizContentState.observe(viewLifecycleOwner) { uiState ->
            when(uiState) {
                is UIState.UINone -> binding.srlQuiz.isRefreshing = false
                is UIState.UILoading -> {
                    setPreLoader(false)
                    binding.srlQuiz.isRefreshing = true
                }
                is UIState.UISuccess -> {
                    adapter.submitList(uiState.data)
                    setSuccess()
                }
                is UIState.UIError -> setError(
                    uiState.errorMessage,
                    isRootInvisible = false
                ) { onSwipeRefresh() }
                else -> Unit
            }
        }
    }

    private fun onSwipeRefresh() {
        viewModel.onSwipeRefresh()
    }
}