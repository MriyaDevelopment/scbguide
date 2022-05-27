package com.spravochnic.scbguide.api.models

import com.spravochnic.scbguide.api.response.TestQuestionsResponse
import com.spravochnic.scbguide.db.entity.TestEntity

fun List<TestQuestionsResponse.Questions>.toTestQuestion(): List<TestEntity> {
    return this.map { test ->
        TestEntity(
            id = test.id ?: 0,
            answer_type = test.answer_type ?: "",
            type = test.type ?: "",
            image = test.image ?: "",
            questions = test.questions ?: "",
            answer_one = test.answer_one ?: "",
            answer_two = test.answer_two ?: "",
            answer_three = test.answer_three ?: ""
        )
    }
}