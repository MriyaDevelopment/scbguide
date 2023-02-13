package com.spravochnic.scbguide.ui.quiz

import androidx.lifecycle.ViewModel
import com.spravochnic.scbguide.repositories.QuizRepository
import com.spravochnic.scbguide.utils.launchOnDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizRepository: QuizRepository
): ViewModel() {

    init {
        onSwipeRefresh()
    }

    fun onSwipeRefresh() {
        launchOnDefault {

        }
    }
}