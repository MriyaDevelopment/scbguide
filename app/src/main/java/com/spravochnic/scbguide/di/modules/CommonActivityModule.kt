package com.spravochnic.scbguide.di.modules

import com.spravochnic.scbguide.ui.main.MainActivity
import com.spravochnic.scbguide.di.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class CommonActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [CommonMainViewModule::class, CommonMainFragmentsBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}