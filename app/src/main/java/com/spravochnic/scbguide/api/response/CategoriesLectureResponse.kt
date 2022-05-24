package com.spravochnic.scbguide.api.response

data class CategoriesLectureResponse(
    val result: String?,
    val error: String?,
    val categories: List<Categories>
) {
    data class Categories(
        val id: Int?,
        val name: String?,
        val type: String?,
        val image: String?,
        val description: String
    )
}