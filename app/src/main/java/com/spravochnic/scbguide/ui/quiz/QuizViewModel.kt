package com.spravochnic.scbguide.ui.quiz

import androidx.lifecycle.ViewModel
import com.spravochnic.scbguide.api.responses.QuizResponse
import com.spravochnic.scbguide.base.network.MutableUIStateFlow
import com.spravochnic.scbguide.repositories.QuizRepository
import com.spravochnic.scbguide.ui.quiz.adapter.QuizAdapter
import com.spravochnic.scbguide.utils.asUIStateFlow
import com.spravochnic.scbguide.utils.emitRequest
import com.spravochnic.scbguide.utils.launchOnDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizRepository: QuizRepository
): ViewModel() {

    private val _quizContentState = MutableUIStateFlow<List<QuizAdapter.ItemQuiz>>()
    val quizContentState = _quizContentState.asUIStateFlow()

    init {
        onSwipeRefresh()
    }

    fun onSwipeRefresh() {
        launchOnDefault {
            _quizContentState.emitRequest(quizRepository.getQuizContent()) { data ->
                data.result.map { QuizAdapter.ItemQuiz.WrapQuiz(it) }
            }
        }
    }
}