package com.spravochnic.scbguide.repositories

import com.spravochnic.scbguide.api.ApiService
import com.spravochnic.scbguide.base.network.BaseApiRepository
import javax.inject.Inject

class QuizRepository @Inject constructor(
    private val apiService: ApiService
) : BaseApiRepository() {

    fun getQuizContent() = doRequest {
        apiService.getQuizContent()
    }
}