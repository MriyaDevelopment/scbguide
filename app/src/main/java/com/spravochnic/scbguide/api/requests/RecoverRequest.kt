package com.spravochnic.scbguide.api.requests

import com.google.gson.annotations.SerializedName

data class RecoverRequest(
    @SerializedName("email") val email: String
)