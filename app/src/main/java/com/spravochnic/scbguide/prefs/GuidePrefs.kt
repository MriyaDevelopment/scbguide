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
        const val DATABASE_IS_FULL = "prefs_database_is_full"
    }

    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    fun setDataBaseIsFull(isFull: Boolean) = prefs.edit { putBoolean(DATABASE_IS_FULL, isFull) }
    fun getDataBaseIsFull() = prefs.getBoolean(DATABASE_IS_FULL, false)
    fun deleteUserToken() = prefs.edit {
        remove(DATABASE_IS_FULL)
    }
}