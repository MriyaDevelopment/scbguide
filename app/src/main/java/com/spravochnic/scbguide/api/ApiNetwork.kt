package com.spravochnic.scbguide.api

import com.spravochnic.scbguide.api.response.CategoriesLectureResponse
import com.spravochnic.scbguide.api.response.LectureCategoriesResponse
import com.spravochnic.scbguide.api.response.TestQuestionsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiNetwork {

    @GET("instruments/getCategoriesLecture.php")
    fun getCategoriesLectureAsync(): Deferred<CategoriesLectureResponse>

    @GET("instruments/getLectureCategory.php")
    fun getLectureCategoryAsync(): Deferred<LectureCategoriesResponse>

    @GET("instruments/getTestQuestions.php")
    fun getTestQuestionsAsync(): Deferred<TestQuestionsResponse>
}