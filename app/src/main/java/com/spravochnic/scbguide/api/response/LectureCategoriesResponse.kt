package com.spravochnic.scbguide.api.response

data class LectureCategoriesResponse(
    val result: String?,
    val error: String?,
    val lecture: List<Lecture>?
) {
    data class Lecture(
        val id: Int?,
        val name: String?,
        val numbers: String?,
        val type: String?,
        val image: String
    )
}