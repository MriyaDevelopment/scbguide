package com.spravochnic.scbguide.ui.api.models

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("info") val info: Info,
    @SerializedName("results") val results: List<Result> = emptyList()
) {
    data class Info(
        @SerializedName("count") val count: Int = 0,
        @SerializedName("pages") val pages: Int = 0,
        @SerializedName("next") val next: String = "",
    )

    data class Result(
        @SerializedName("id") val id: Int = 0,
        @SerializedName("name") val name: String = "",
        @SerializedName("image") val image: String = ""
    )
}