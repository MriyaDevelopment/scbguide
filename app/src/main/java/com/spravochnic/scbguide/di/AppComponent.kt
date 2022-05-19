package com.spravochnic.scbguide.di

import android.app.Application
import com.spravochnic.scbguide.App
import com.spravochnic.scbguide.di.modules.CommonActivityModule
import com.spravochnic.scbguide.di.modules.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, CommonActivityModule::class, NetworkModule::class])
interface AppComponent: AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder
        fun build(): AppComponent
    }

    override fun inject(app: App)
}