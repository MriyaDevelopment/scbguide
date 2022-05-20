package com.spravochnic.scbguide.di.modules

import android.content.Context
import androidx.room.Room
import com.spravochnic.scbguide.App
import com.spravochnic.scbguide.db.DataBase
import com.spravochnic.scbguide.utils.ContextUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    companion object {
        private const val DATABASE = "scbGuide"
    }

    @Singleton
    @Provides
    fun provideDataBase(context: Context): DataBase {
        return Room.databaseBuilder(
            context,
            DataBase::class.java,
            DATABASE
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideContext(context: Context, instance: App): ContextUtils {
        return ContextUtils(context = context, instance = instance)
    }
}