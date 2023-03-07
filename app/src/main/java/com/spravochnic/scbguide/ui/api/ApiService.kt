package com.spravochnic.scbguide.ui.api

import com.google.gson.GsonBuilder
import com.spravochnic.scbguide.BuildConfig
import com.spravochnic.scbguide.ui.api.models.CharacterResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET("character")
    suspend fun getCharacter(
       @Query("page") page: Int
    ): Response<CharacterResponse>

    companion object {

        private val converter: GsonConverterFactory = GsonConverterFactory.create(GsonBuilder().setLenient().create())

        val API: ApiService by lazy {
            val okHttpClient = OkHttpClient.Builder().apply {
                callTimeout(60, TimeUnit.SECONDS)
                connectTimeout(20, TimeUnit.SECONDS)
                readTimeout(20, TimeUnit.SECONDS)
                writeTimeout(20, TimeUnit.SECONDS)
            }

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                    okHttpClient.addInterceptor(this)
                }
            }
            val retrofit = Retrofit.Builder()
                .client(okHttpClient.build())
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(converter)
                .build()

            retrofit.create(ApiService::class.java)
        }
    }
}