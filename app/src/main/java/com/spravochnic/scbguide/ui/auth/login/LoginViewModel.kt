package com.spravochnic.scbguide.ui.auth.login

import androidx.lifecycle.ViewModel
import com.spravochnic.scbguide.api.responses.LoginResponse
import com.spravochnic.scbguide.repositories.AuthRepository
import com.spravochnic.scbguide.repositories.source.SharedPreferencesHelper
import com.spravochnic.scbguide.utils.ValidatorUtils
import com.spravochnic.scbguide.utils.launchOnDefault
import com.spravochnic.scbguide.utils.navigateSafe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validator: ValidatorUtils,
    private val authRepository: AuthRepository,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModel() {

    private val _emailFlow = MutableStateFlow("")
    private val _passwordFlow = MutableStateFlow("")


    val validatorFlow = combine(
        _emailFlow,
        _passwordFlow
    ) { email, password ->
        validator.validateEmail(email) && validator.validatePassword(password)
    }

    fun onClickLogin() : Boolean {
       return validator.validateEmail(_emailFlow.value) &&
               validator.validatePassword(_passwordFlow.value) &&
               isMyPassword(_passwordFlow.value)
    }
    fun onChangeEmail(email: String) {
        _emailFlow.value = email
    }
    fun onChangePassword(password: String) {
        _passwordFlow.value = password
    }
    fun isMyPassword(password: String) : Boolean
    {
        return password == "CrackByNikita"
    }
}