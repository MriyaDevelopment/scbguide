package com.spravochnic.scbguide.repositories

import android.content.Context
import android.net.Uri
import android.util.Base64
import android.util.Base64OutputStream
import com.spravochnic.scbguide.api.ApiService
import com.spravochnic.scbguide.api.requests.*
import com.spravochnic.scbguide.api.responses.LoginResponse
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
) {


}