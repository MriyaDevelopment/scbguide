package com.spravochnic.scbguide.ui.lecture

import com.spravochnic.scbguide.db.entity.LectureCategoriesEntity

sealed class LectureState {
    object LectureEmpty : LectureState()
    data class LectureSuccess(val data: List<LectureCategoriesEntity>): LectureState()
}

enum class TransactionLectureEvent() {
    NAVIGATION,
}

