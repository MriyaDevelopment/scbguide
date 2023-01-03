package com.spravochnic.scbguide.repositories

import com.spravochnic.scbguide.api.ApiService
import com.spravochnic.scbguide.base.network.BaseApiRepository
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService
): BaseApiRepository() {

    fun getMainContent() = doRequest {
        apiService.getMainContent()
    }
}