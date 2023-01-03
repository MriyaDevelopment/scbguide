package com.spravochnic.scbguide.di.module

import android.content.Context
import com.spravochnic.scbguide.App
import com.spravochnic.scbguide.repositories.source.SharedPreferencesHelper
import com.spravochnic.scbguide.utils.Constants.PREFERENCES_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApp(@ApplicationContext context: Context): App = context.applicationContext as App

    @Singleton
    @Provides
    fun provideSharedPreferenceHelper(@ApplicationContext context: Context): SharedPreferencesHelper =
        SharedPreferencesHelper(context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE))
}