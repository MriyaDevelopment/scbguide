package com.spravochnic.scbguide.repositories

import android.content.Context
import android.net.Uri
import android.util.Base64
import android.util.Base64OutputStream
import com.spravochnic.scbguide.api.ApiService
import com.spravochnic.scbguide.api.requests.*
import com.spravochnic.scbguide.api.responses.LoginResponse
import com.spravochnic.scbguide.base.network.BaseApiRepository
import com.spravochnic.scbguide.utils.fileToBase64
import com.spravochnic.scbguide.utils.getFile
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    @ApplicationContext val context: Context
) : BaseApiRepository() {

    fun login(email: String, password: String) = doRequest {
        apiService.login(LoginRequest(email = email, password = password))
    }

    fun register(
        name: String,
        email: String,
        password: String,
        avatar: Uri? = null,
        aboutMe: String? = null
    ) = doRequest {
        apiService.register(
            RegisterRequest(
                name = name,
                email = email,
                password = password,
                avatar = avatar?.getFile(context.contentResolver)?.fileToBase64(),
                aboutMe = aboutMe
            )
        )
    }

    fun sendLetter(email: String) = doRequest {
        apiService.sendLetter(RecoverRequest(email = email))
    }

    fun code(code: String) = doRequest {
        apiService.code(CodeRequest(code = code))
    }

    fun changePass(password: String) = doRequest {
        apiService.changePass(ChangePassRequest(password))
    }
}