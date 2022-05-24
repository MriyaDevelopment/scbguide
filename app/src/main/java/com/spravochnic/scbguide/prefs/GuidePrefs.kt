package com.spravochnic.scbguide.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GuidePrefs @Inject constructor(context: Context) {

    companion object {
        const val PREF_USER_TOKEN = "pref_user_token"
    }
    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    fun setUserToken(userToken: String) = prefs.edit { putString(PREF_USER_TOKEN, userToken) }
    fun getUserToken() = prefs.getString(PREF_USER_TOKEN, null)
    fun deleteUserToken() = prefs.edit {
        remove(PREF_USER_TOKEN)
    }
}