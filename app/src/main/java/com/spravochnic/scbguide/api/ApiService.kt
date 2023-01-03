package com.spravochnic.scbguide.api

import com.spravochnic.scbguide.api.responses.MainResponse
import retrofit2.Response
import retrofit2.http.POST

interface ApiService {

    @POST("getMainContent")
    suspend fun getMainContent(): Response<MainResponse>
}