package com.spravochnic.scbguide.api.responses

import com.google.gson.annotations.SerializedName
import com.spravochnic.scbguide.utils.enums.QuizContentType

data class QuizResponse(
    @SerializedName("result") val result: List<QuizResult>
) {
    data class QuizResult(
        @SerializedName("id") val id: String,
        @SerializedName("type") val type: QuizContentType,
        @SerializedName("numberOfQuestions") val numberOfQuestions: String,
    )
}