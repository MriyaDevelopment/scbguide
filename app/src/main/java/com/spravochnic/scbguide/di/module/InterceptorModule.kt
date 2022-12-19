package com.spravochnic.scbguide.di.module

import android.util.Log
import com.spravochnic.scbguide.utils.RetrofitLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
}


