package com.spravochnic.scbguide.di.module.api.services

import com.spravochnic.scbguide.api.ApiService
import com.spravochnic.scbguide.di.annotations.ApiRetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun provideVitaApi(@ApiRetrofitClient retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}