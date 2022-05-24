package com.spravochnic.scbguide.api.models

import com.spravochnic.scbguide.api.response.CategoriesLectureResponse
import com.spravochnic.scbguide.db.entity.LectureEntity


fun List<CategoriesLectureResponse.Categories>.toLectureCategories(): List<LectureEntity> {
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