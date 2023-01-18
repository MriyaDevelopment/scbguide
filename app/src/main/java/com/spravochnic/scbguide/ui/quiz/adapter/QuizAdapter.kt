package com.spravochnic.scbguide.ui.quiz.adapter

import com.spravochnic.scbguide.api.responses.QuizResponse
import com.spravochnic.scbguide.base.ui.adapter.BaseListAdapter
import javax.inject.Inject

class QuizAdapter @Inject constructor(): BaseListAdapter<QuizAdapter.ItemQuiz>() {
    override fun build() {
    }

    sealed class ItemQuiz {
        data class WrapQuiz(val result: QuizResponse.QuizResult): ItemQuiz()
    }
}