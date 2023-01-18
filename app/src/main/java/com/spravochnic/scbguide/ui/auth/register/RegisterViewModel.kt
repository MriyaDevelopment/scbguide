package com.spravochnic.scbguide.ui.auth.register

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Base64
import android.util.Base64OutputStream
import androidx.lifecycle.ViewModel
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.api.responses.RegisterResponse
import com.spravochnic.scbguide.base.network.MutableUIStateFlow
import com.spravochnic.scbguide.repositories.AuthRepository
import com.spravochnic.scbguide.repositories.source.SharedPreferencesHelper
import com.spravochnic.scbguide.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val validatorUtils: ValidatorUtils,
    private val authRepository: AuthRepository,
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    private val managerUtils: ManagerUtils
) : ViewModel() {

    private val _registerState = MutableUIStateFlow<String>()
    val registerState = _registerState.asUIStateFlow()

    private val _avatarFlow = MutableStateFlow<Uri>(Uri.EMPTY)
    val avatarFlow = _avatarFlow.asStateFlow()

    private val _nameFlow = MutableStateFlow("")
    private val _emailFlow = MutableStateFlow("")
    private val _passwordFlow = MutableStateFlow("")
    private val _repeatPasswordFlow = MutableStateFlow("")
    private val _aboutMeFlow = MutableStateFlow("")

    val validatorFlow = combine(
        _nameFlow,
        _emailFlow,
        _passwordFlow,
        _repeatPasswordFlow,
    ) { name, email, password, repeatPassword ->
        validatorUtils.validateName(name)
                && validatorUtils.validateEmail(email)
                && validatorUtils.validatePassword(password)
                && password.trim() == repeatPassword.trim()
    }

    fun onPickAvatar(uri: Uri) {
        _avatarFlow.value = uri
    }

    fun onChangeName(name: String) {
        _nameFlow.value = name
    }

    fun onChangeEmail(email: String) {
        _emailFlow.value = email
    }

    fun onChangePassword(password: String) {
        _passwordFlow.value = password
    }

    fun onChangeRepeatPassword(repeatPassword: String) {
        _repeatPasswordFlow.value = repeatPassword
    }

    fun onChangeAboutMe(aboutMe: String) {
        _aboutMeFlow.value = aboutMe
    }

    fun onClickRegister() {
        launchOnDefault {
            _registerState.emitRequest(authRepository.register(
                name = _nameFlow.value,
                email = _emailFlow.value,
                password = _passwordFlow.value,
                avatar = if (avatarFlow.value != Uri.EMPTY) avatarFlow.value else null,
                aboutMe = _aboutMeFlow.value.ifEmpty { null }
            )) {
                sharedPreferencesHelper.apiToken = it.api_token
                managerUtils.getString(R.string.register_success)
            }
        }
    }
}