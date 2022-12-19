package com.spravochnic.scbguide.base.network

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.spravochnic.scbguide.utils.MutableStateFlowSimulator
import com.spravochnic.scbguide.utils.StateFlowSimulator
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow

@Suppress("FunctionName")
fun <R> MutableUIStateFlow(
    sharedFlow: MutableSharedFlow<UIState<R>> = MutableSharedFlow(replay = 1)
) = MutableStateFlowSimulator(sharedFlow)

fun <R> StateFlowSimulator<UIState<R>>.successValue() = (value as? UIState.UISuccess)?.data

sealed class UIState<T> {
    class UINone<T> : UIState<T>()
    data class UILoading<T>(val progress: Float = 0f) : UIState<T>()
    data class UIPreSuccess<T>(val data: T) : UIState<T>()
    data class UISuccess<T>(val data: T) : UIState<T>()
    data class UIError<T>(val errorCode: Int, val errorMessage: String) : UIState<T>() {
        fun showSnackBar(
            anchorView: View,
            buttonText: String? = null,
            onButtonClicked: (() -> Unit)? = null,
            transactionY: Boolean = false
        ) {
            com.spravochnic.scbguide.utils.showSnackBar(
                errorMessage,
                anchorView,
                buttonText,
                onButtonClicked,
                Snackbar.LENGTH_SHORT,
                transactionY
            )
        }

        companion object {
            /**
             *  Генерирует ошибку для emitRequest
             */
            fun createError(errorCode: Int, errorMessage: String) = flow<NetworkResult<Unit>> {
                emit(NetworkResult.Error(errorCode = errorCode, message = errorMessage))
            }
        }
    }

    inline fun targetSuccess(action: (T) -> Unit) {
        if (this is UIState.UISuccess) action(data)
    }
}
