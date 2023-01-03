package com.spravochnic.scbguide.base.network

import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.flow
import okio.Buffer
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import kotlin.coroutines.cancellation.CancellationException

abstract class BaseApiRepository {

    protected fun <T> doRequest(
        requestConfig: RequestConfig = RequestConfig(),
        request: suspend () -> Response<T>
    ) = flow {
        emit(NetworkResult.Loading())
        emit(safeApiCall(requestConfig, request))
    }

    suspend fun <T> safeApiCall(
        requestConfig: RequestConfig = RequestConfig(),
        apiCall: suspend () -> Response<T>,
    ): NetworkResult<T> {
        return try {
            val response = apiCall()

            if (response.isSuccessful) {
                val body = response.body() ?: return createError(response, requestConfig)
                return NetworkResult.Success(body)
            }

            createError(response, requestConfig)
        } catch (e: Exception) {
            return createError(e)
        }
    }

    private fun <T> createError(e: Exception): NetworkResult.Error<T> {
        val error = when (e) {
            is JsonSyntaxException -> RequestError.JSON
            is CancellationException -> RequestError.COROUTINE_CANCEL
            is ConnectException, is SocketTimeoutException, is SocketException, is IOException -> RequestError.CONNECT
            else -> RequestError.NONE
        }

        return NetworkResult.Error(
            if (error != RequestError.NONE) error.errorMessage else e.localizedMessage,
            0
        )
    }

    private fun <T> createError(
        response: Response<T>,
        requestConfig: RequestConfig
    ): NetworkResult.Error<T> {
        runCatching {
            response.raw().request.body?.let {
                val buffer = Buffer()
                it.writeTo(buffer)
                buffer.readUtf8()
            }
        }.getOrNull().takeIf { !requestConfig.isIgnoredRequestBody }

        val errorMessage = if (response.isSuccessful) {
            response.message()
        } else {
            runCatching {
                JSONObject(response.errorBody()?.string().toString())[ERROR_MESSAGE_KEY].toString()
            }.getOrNull() ?: response.message()
        }

        return NetworkResult.Error(errorMessage, response.code())
    }

    private enum class RequestError(val errorMessage: String, val isIgnored: Boolean = false) {
        CONNECT("Не удалось подключиться к серверу", true),
        JSON("Не удалось декодировать данные"),
        COROUTINE_CANCEL("", true),
        NONE("Неизвестная ошибка")
    }

    companion object {
        private const val ERROR_MESSAGE_KEY = "message"
    }
}

data class RequestConfig(
    val isIgnoredRequestBody: Boolean = false,
    val ignoreLogCodes: List<Int> = emptyList(),
    val ignoreLogErrorMessages: List<String> = emptyList()
)