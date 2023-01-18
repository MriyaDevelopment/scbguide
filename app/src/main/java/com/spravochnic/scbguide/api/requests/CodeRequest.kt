package com.spravochnic.scbguide.api.requests

import com.google.gson.annotations.SerializedName

data class CodeRequest(
    @SerializedName("code") val code: String
)