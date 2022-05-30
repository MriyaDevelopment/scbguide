package com.spravochnic.scbguide.api.models

import com.spravochnic.scbguide.R
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

data class TestCategories(
    val id: Int,
    val name: String,
    val image: Int,
    val questions: String,
    val type: String,
)

fun testCategories(): List<TestCategories> {
    return listOf(
        TestCategories(
            id = 1,
            name = "Легкий уровень",
            image = R.drawable.ic_beginner,
            questions = "15 вопросов",
            type = "Beginner"
        ),
        TestCategories(
            id = 2,
            name = "Средний уровень",
            image = R.drawable.ic_expert,
            questions = "15 вопросов",
            type = "Expert"
        ),
        TestCategories(
            id = 3,
            name = "Тяжелый уровень",
            image = R.drawable.ic_legend,
            questions = "15 вопросов",
            type = "Legent"
        )
    )
}