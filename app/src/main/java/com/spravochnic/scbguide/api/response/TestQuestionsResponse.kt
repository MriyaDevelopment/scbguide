package com.spravochnic.scbguide.api.response

data class TestQuestionsResponse(
    val result: String?,
    val error: String?,
    val questList: List<Questions>?
) {
    data class Questions(
        val id: Int?,
        val answer_type: String?,
        val type: String?,
        val image: String?,
        val questions: String?,
        val answer_one: String?,
        val answer_two: String?,
        val answer_three: String?
    )
}