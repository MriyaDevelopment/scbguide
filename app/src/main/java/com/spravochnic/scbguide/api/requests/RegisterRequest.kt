package com.spravochnic.scbguide.api.requests

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("avatar") val avatar: String? = null,
    @SerializedName("aboutMe") val aboutMe: String? = null
) {
}