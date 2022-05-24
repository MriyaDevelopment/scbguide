package com.spravochnic.scbguide.api

import com.spravochnic.scbguide.api.response.CategoriesLectureResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiNetwork {

    @GET("instruments/getCategoriesLecture.php")
    fun getCategoriesLectureAsync(): Deferred<CategoriesLectureResponse>
}