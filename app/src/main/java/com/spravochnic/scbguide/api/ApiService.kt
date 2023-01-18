package com.spravochnic.scbguide.api

import com.spravochnic.scbguide.api.requests.*
import com.spravochnic.scbguide.api.responses.LoginResponse
import com.spravochnic.scbguide.api.responses.MainResponse
import com.spravochnic.scbguide.api.responses.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/getMainContent")
    suspend fun getMainContent(): Response<MainResponse>

    @POST("/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @POST("/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<RegisterResponse>

    @POST("/sendLetter")
    suspend fun sendLetter(
        @Body recoverRequest: RecoverRequest
    ): Response<Unit>

    @POST("/code")
    suspend fun code(
        @Body codeRequest: CodeRequest
    ): Response<Unit>

    @POST("/changePass")
    suspend fun changePass(
        @Body changePassRequest: ChangePassRequest
    ): Response<Unit>
}