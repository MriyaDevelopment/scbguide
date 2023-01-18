package com.spravochnic.scbguide.api.requests

import com.google.gson.annotations.SerializedName

data class ChangePassRequest(
    @SerializedName("password") val password: String
)