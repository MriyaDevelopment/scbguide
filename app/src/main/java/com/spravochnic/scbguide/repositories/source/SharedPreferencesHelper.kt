package com.spravochnic.scbguide.repositories.source

import android.content.SharedPreferences
import androidx.core.content.edit
import com.spravochnic.scbguide.base.source.PrefDelegateModel

class SharedPreferencesHelper(sharedPreferences: SharedPreferences) :
    PrefDelegateModel(sharedPreferences) {

    var apiToken by stringPref()
    var fcmToken by stringPref()

    fun logout() {
        prefs.edit(commit = true) { clear() }
    }
}