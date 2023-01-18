package com.spravochnic.scbguide.ui.auth.recovery_service.password

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(): ViewModel() {

    private val _passwordFlow = MutableStateFlow("")
    val passwordFlow = _passwordFlow.asStateFlow()

    private val repeatPasswordFlow = MutableStateFlow("")

    val validateFlow = combine(
        _passwordFlow,
        repeatPasswordFlow
    ) { password, repeat ->
        password.trim().length >= 6 && password.trim() == repeat.trim()
    }

    fun onChangePassword(password: String) {
        _passwordFlow.value = password
    }

    fun onChangeRepeatPassword(repeatPassword: String) {
        repeatPasswordFlow.value = repeatPassword
    }
}