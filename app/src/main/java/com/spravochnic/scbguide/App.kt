package com.spravochnic.scbguide

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        reference = this
    }

    companion object {
        private var reference: App? = null

        fun getInstance(): App {
            return reference ?: throw IllegalStateException("App is not initialized.")
        }
    }
}