package com.spravochnic.scbguide.ui.auth.login

import androidx.lifecycle.ViewModel
import com.spravochnic.scbguide.api.responses.LoginResponse
import com.spravochnic.scbguide.base.network.MutableUIStateFlow
import com.spravochnic.scbguide.repositories.AuthRepository
import com.spravochnic.scbguide.repositories.source.SharedPreferencesHelper
import com.spravochnic.scbguide.utils.ValidatorUtils
import com.spravochnic.scbguide.utils.asUIStateFlow
import com.spravochnic.scbguide.utils.emitRequest
import com.spravochnic.scbguide.utils.launchOnDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validator: ValidatorUtils,
    private val authRepository: AuthRepository,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModel() {

    private val _emailFlow = MutableStateFlow("")
    private val _passwordFlow = MutableStateFlow("")

    private val _loginState = MutableUIStateFlow<LoginResponse>()
    val loginState = _loginState.asUIStateFlow()

    val validatorFlow = combine(
        _emailFlow,
        _passwordFlow
    ) { email, password ->
        validator.validateEmail(email) && validator.validatePassword(password)
    }

    fun onClickLogin() {
        launchOnDefault {
            _loginState.emitRequest(
                authRepository.login(
                    email = _emailFlow.value,
                    password = _passwordFlow.value
                )
            ) {
                sharedPreferencesHelper.apiToken = it.api_token
                it
            }
        }
    }

    fun onChangeEmail(email: String) {
        _emailFlow.value = email
    }

    fun onChangePassword(password: String) {
        _passwordFlow.value = password
    }
}