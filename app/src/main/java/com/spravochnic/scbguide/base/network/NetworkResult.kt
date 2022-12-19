package com.spravochnic.scbguide.base.network

sealed class NetworkResult<T>(
    val data: T? = null,
    private val _message: String? = null,
    val errorCode: Int = 0
) {
    val message: String
        get() = if (errorCode >= 500) "Внутренняя ошибка сервера" else _message ?: ""

    class Loading<T>(val progress: Float = 0f) : NetworkResult<T>()

    class Success<T>(data: T) : NetworkResult<T>(data) {
        val successData = data //Всегда not-null
    }

    class Error<T>(message: String, errorCode: Int, data: T? = null) :
        NetworkResult<T>(data, message, errorCode) {
        val errorMessage = super.message //Всегда not-null
    }

    fun targetSuccess() = (this as? Success)?.successData

    inline fun target(
        onLoading: (Float) -> Unit = {},
        onError: (String) -> Unit = {},
        onSuccess: (T) -> Unit = {},
    ) {
        when (this) {
            is Loading -> onLoading.invoke(progress)
            is Success -> onSuccess.invoke(successData)
            is Error -> onError.invoke(errorMessage)
        }
    }

    fun errorString(): String {
        return if (this is Success) "Success"
        else message
    }
}