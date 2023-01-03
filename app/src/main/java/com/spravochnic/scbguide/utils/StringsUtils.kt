package com.spravochnic.scbguide.utils

import android.content.Context
import androidx.annotation.ArrayRes
import com.spravochnic.scbguide.R

enum class WordDeclension(@ArrayRes private val id: Int) {
    THEMES(R.array.themes_declension);

    fun declension(num: Number, context: Context): String {
        val numType = num.toInt()
        val declensions = context.resources.getStringArray(id)

        return if (numType % 100 / 10 == 1) {
            declensions[2]
        } else when (numType % 10) {
            1 -> declensions[0]
            2, 3, 4 -> declensions[1]
            else -> declensions[2]
        }
    }
}