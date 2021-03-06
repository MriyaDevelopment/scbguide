package com.spravochnic.scbguide.di

import android.content.Context
import com.spravochnic.scbguide.App
import com.spravochnic.scbguide.di.modules.AppModule
import com.spravochnic.scbguide.di.modules.CommonActivityModule
import com.spravochnic.scbguide.di.modules.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, CommonActivityModule::class, NetworkModule::class, AppModule::class])
interface AppComponent: AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        fun application(@BindsInstance instance: App): Builder
        fun context(@BindsInstance context: Context): Builder
        fun build(): AppComponent
    }

    override fun inject(app: App)
}