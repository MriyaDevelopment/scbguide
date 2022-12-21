package com.spravochnic.scbguide.ui.quiz

import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.base.ui.fragments.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentQuizBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : BaseFragment<FragmentQuizBinding>(FragmentQuizBinding::inflate) {

    private val viewModel by viewModels<QuizViewModel>()

    override fun onBackPressed() {
        navViewModel.onClickHardDeepLink("".toUri(), R.id.main_nav_graph)
    }
}