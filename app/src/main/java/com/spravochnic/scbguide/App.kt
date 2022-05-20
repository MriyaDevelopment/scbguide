package com.spravochnic.scbguide

import com.spravochnic.scbguide.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {

    private val applicationInjector = DaggerAppComponent.builder().application(this).context(this).build()
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        applicationInjector
}