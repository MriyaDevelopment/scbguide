package com.spravochnic.scbguide.utils.enums

import com.google.gson.annotations.SerializedName

enum class QuizContentType {
    @SerializedName("quiz") QUIZ,
    @SerializedName("my_quiz_hall") MY_QUIZ_HALL,
    @SerializedName("community") COMMUNITY
}