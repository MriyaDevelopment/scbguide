package com.spravochnic.scbguide.api.responses

import com.google.gson.annotations.SerializedName
import com.spravochnic.scbguide.utils.WordDeclension
import com.spravochnic.scbguide.utils.enums.MainContentType

data class MainResponse(
    @SerializedName("result") val result: List<Result>,
) {
    data class Result(
        @SerializedName("type") val type: String?,
        @SerializedName("numberOfTopics") val numberOfTopics: String,
    ) {
        val mainContentType: MainContentType?
            get() = type?.let(MainContentType::valueOf)
    }
}