package com.spravochnic.scbguide.ui.main

import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.db.entity.LectureEntity

sealed class MainState {
    object Empty: MainState()
    object LoadingMainState: MainState()
    data class SuccessMainState(val data: List<LectureEntity>): MainState()
    data class ErrorMainState(val errorMessage: String?): MainState()
    data class FailureMainState(val failure: String?): MainState()
}

enum class StatusBarColor(val color: Int) {
    DARK_YELLOW(R.color.dark_yellow_0D6D6666),
    TRANSPARENT(R.color.transparent)
}

enum class TransactionEvent() {
    LECTURE,
    TEST,
}