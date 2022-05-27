package com.spravochnic.scbguide.api.models

import com.spravochnic.scbguide.api.response.CategoriesLectureResponse
import com.spravochnic.scbguide.api.response.LectureCategoriesResponse
import com.spravochnic.scbguide.db.entity.LectureCategoriesEntity
import com.spravochnic.scbguide.db.entity.LectureEntity


fun List<CategoriesLectureResponse.Categories>.toCategoriesLecture(): List<LectureEntity> {
    return this.map { categories ->
        LectureEntity(
            id = categories.id ?: 0,
            name = categories.name ?: "",
            type = categories.type ?: "",
            image = categories.image ?: "",
            description = categories.description ?: ""
        )
    }
}

fun List<LectureCategoriesResponse.Lecture>.toLectureCategories(): List<LectureCategoriesEntity> {
    return this.map { lecture ->
        LectureCategoriesEntity(
            id = lecture.id ?: 0,
            name = lecture.name ?: "",
            numbers = lecture.numbers ?: "",
            type = lecture.type ?: "",
            image = lecture.image ?: ""
        )
    }
}