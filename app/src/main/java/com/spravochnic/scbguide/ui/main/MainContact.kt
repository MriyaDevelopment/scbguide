package com.spravochnic.scbguide.ui.main

import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.db.entity.LectureEntity
import com.spravochnic.scbguide.ui.lecture.LectureState
import java.lang.Error

sealed class MainState {
    object Empty: MainState()
    object LoadingMainState: MainState()
    object SuccessMainState: MainState()
    object ErrorMainState: MainState()
    object FailureMainState: MainState()
    data class ErrorLectureMainState(val errorMessage: String): MainState()
    data class ErrorTestMainState(val errorMessage: String): MainState()
    data class ErrorCategoriesLectureMainState(val errorMessage: String): MainState()
}

enum class StatusBarColor(val color: Int) {
    DARK_YELLOW(R.color.dark_yellow_0D6D6666),
    TRANSPARENT(R.color.transparent)
}

enum class TransactionEvent() {
    LECTURE,
    TEST,
}