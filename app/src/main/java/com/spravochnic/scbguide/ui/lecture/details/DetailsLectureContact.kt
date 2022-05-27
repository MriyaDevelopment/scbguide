package com.spravochnic.scbguide.ui.lecture.details

import com.spravochnic.scbguide.db.entity.LectureEntity

sealed class DetailsLectureState{
    object Empty: DetailsLectureState()
    data class setDataLecture(val data: List<LectureEntity>): DetailsLectureState()
}

sealed class DetailState{
    object Empty: DetailState()
    data class setDataDetail(val data: LectureEntity): DetailState()
}

enum class TransactionDetailsLectureEvent{
    NAVIGATION
}