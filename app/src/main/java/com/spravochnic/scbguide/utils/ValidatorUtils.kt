package com.spravochnic.scbguide.utils

import java.util.regex.Pattern
import javax.inject.Inject

class ValidatorUtils @Inject constructor() {

    private val emailValidator = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,64})$"
    private val emailPattern: Pattern = Pattern.compile(emailValidator)

    fun validateEmail(hex: String): Boolean {
        val matcher = emailPattern.matcher(hex)
        return matcher.matches()
    }

    fun validatePassword(hex: String): Boolean {
        return hex.length >= 6 && hex.trim().isNotEmpty()
    }

    fun validateName(hex: String): Boolean {
        return hex.trim().isNotEmpty()
    }
}