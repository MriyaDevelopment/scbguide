package com.spravochnic.scbguide.di.module

import android.util.Log
import com.spravochnic.scbguide.di.annotations.HttpRequestInterceptor
import com.spravochnic.scbguide.repositories.source.SharedPreferencesHelper
import com.spravochnic.scbguide.utils.RetrofitLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InterceptorModule {

    @Provides
    @Singleton
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor.Logger { message ->
            Log.i("okhttp.OkHttpClient", message)
            RetrofitLogger.message.add(message)
        }
        return HttpLoggingInterceptor(logger).apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    @HttpRequestInterceptor
    fun requestInterceptor(sharedPreferencesHelper: SharedPreferencesHelper): Interceptor =
        Interceptor {
            val original = it.request()

            val request: Request = original.newBuilder()
                .header("Authorization", sharedPreferencesHelper.apiToken)
                .method(original.method, original.body)
                .build()

            it.proceed(request)
        }
}


