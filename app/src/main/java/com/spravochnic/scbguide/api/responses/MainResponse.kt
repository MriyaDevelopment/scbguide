package com.spravochnic.scbguide.api.responses

import com.google.gson.annotations.SerializedName
import com.spravochnic.scbguide.utils.enums.MainContentType

data class MainResponse(
    @SerializedName("result") val result: List<MainResult>,
) {
    data class MainResult(
        @SerializedName("type") val type: MainContentType?,
        @SerializedName("numberOfTopics") val numberOfTopics: String,
    )
}