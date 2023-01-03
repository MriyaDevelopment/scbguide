package com.spravochnic.scbguide.di.module

import com.google.gson.GsonBuilder
import com.spravochnic.scbguide.di.annotations.ApiRetrofitClient
import com.spravochnic.scbguide.di.annotations.HttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create(
        GsonBuilder().setLenient().create())

    @Provides
    @Singleton
    @ApiRetrofitClient
    fun apiRetrofit(
        @HttpClient okHttpClient: OkHttpClient,
        factory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://ovz5.j04713753.0n03n.vps.myjino.ru/public/api/")
            .client(okHttpClient)
            .addConverterFactory(factory)
            .build()
}