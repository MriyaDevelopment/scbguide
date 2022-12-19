package com.spravochnic.scbguide.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.spravochnic.scbguide.base.network.NetworkResult
import com.spravochnic.scbguide.base.network.UIState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

fun <T> Flow<T>.observe(lifecycleOwner: LifecycleOwner, block: suspend (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collect {
                block(it)
            }
        }
    }
}

fun <T> Flow<T>.observeLatest(lifecycleOwner: LifecycleOwner, block: suspend (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collectLatest(block)
        }
    }
}

suspend inline fun <T, R> MutableSharedFlow<UIState<R>>.emitRequest(
    request: Flow<NetworkResult<T>>,
    isOnlySuccess: Boolean = false,
    crossinline onLoading: (Float) -> Unit = { },
    crossinline onError: (Int, String) -> String = { _, message -> message },
    crossinline onSuccess: suspend (T) -> R,
) {
    subscriptionCount.first { it > 0 }

    request.collect {
        val state = when (it) {
            is NetworkResult.Loading -> {
                if (isOnlySuccess) return@collect
                onLoading.invoke(it.progress)
                UIState.UILoading(it.progress)
            }
            is NetworkResult.Success -> {
                val data = onSuccess.invoke(it.successData)
                emit(UIState.UIPreSuccess(data))
                UIState.UISuccess(data)
            }
            is NetworkResult.Error -> {
                UIState.UIError(it.errorCode, onError.invoke(it.errorCode, it.errorMessage))
            }
        }

        if (it is NetworkResult.Loading) return@collect emit(state)

        if (!isOnlySuccess) emit(UIState.UINone())
        emit(state)

        if (!isOnlySuccess && it is NetworkResult.Error) {
            emit(UIState.UINone())
        }
    }
}

fun <T> MutableStateFlowSimulator<UIState<T>>.asUIStateFlow(): StateFlowSimulator<UIState<T>> = this


class MutableStateFlowSimulator<R>(
    flow: MutableSharedFlow<R>
) : StateFlowSimulator<R>(flow), MutableSharedFlow<R> {
    override var value: R? = null
        private set

    fun asStateFlowSimulator(): StateFlowSimulator<R> = this

    override suspend fun emit(value: R) = flow.emit(value).also { this.value = value }
    override fun tryEmit(value: R) = flow.tryEmit(value).also { this.value = value }

    override val subscriptionCount: StateFlow<Int>
        get() = flow.subscriptionCount

    @ExperimentalCoroutinesApi
    override fun resetReplayCache() = flow.resetReplayCache()
}

abstract class StateFlowSimulator<R>(
    protected val flow: MutableSharedFlow<R>
) : SharedFlow<R> by flow {
    abstract val value: R?
}