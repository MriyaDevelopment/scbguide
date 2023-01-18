package com.spravochnic.scbguide.ui.auth.recovery_service

import androidx.lifecycle.ViewModel
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.base.network.MutableUIStateFlow
import com.spravochnic.scbguide.repositories.AuthRepository
import com.spravochnic.scbguide.utils.ManagerUtils
import com.spravochnic.scbguide.utils.asUIStateFlow
import com.spravochnic.scbguide.utils.emitRequest
import com.spravochnic.scbguide.utils.launchOnDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class RecoverServiceViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val managerUtils: ManagerUtils
) : ViewModel() {

    private val _recoverState = MutableUIStateFlow<Unit>()
    val recoverState = _recoverState.asUIStateFlow()

    private val _codeState = MutableUIStateFlow<Unit>()
    val codeState = _codeState.asUIStateFlow()

    private val _emailFlow = MutableStateFlow("")
    val emailFlow = _emailFlow.asStateFlow()

    private val _changePassState = MutableUIStateFlow<Unit>()
    val changePassState = _changePassState.asUIStateFlow()

    private val _startTimerFlow = Channel<Unit>(Channel.CONFLATED)
    val startTimerFlow = _startTimerFlow.receiveAsFlow()

    private val _errorFlow = Channel<String>()
    val errorFlow = _errorFlow.receiveAsFlow()

    private val _successFlow = Channel<String>()
    val successFlow = _successFlow.receiveAsFlow()

    private val _selectorFlow = Channel<SelectorRecover>(Channel.CONFLATED)
    val selectorFlow = _selectorFlow.receiveAsFlow()

    fun onClickEmail(phone: String, isSmsCode: Boolean = false) {
        _emailFlow.value = phone
        onClickCodeRequest(isSmsCode)
    }

    fun onClickCodeRequest(isSmsCode: Boolean = true) {
        launchOnDefault {
            _recoverState.emitRequest(authRepository.sendLetter(emailFlow.value),
                onError = { _, message ->
                    _errorFlow.trySend(message)
                    message
                }) {
                if (!isSmsCode) {
                    onSelectNextItem()
                }
                _startTimerFlow.trySend(Unit)
            }
        }
    }

    fun onClickCode(code: String) {
        launchOnDefault {
            _codeState.emitRequest(authRepository.code(code),
                onError = { _, message ->
                    _errorFlow.trySend(message)
                    message
                }) {
                onSelectNextItem()
            }
        }
    }

    fun onClickChangePass(password: String) {
        launchOnDefault {
            _changePassState.emitRequest(authRepository.changePass(password),
                onError = { _, message ->
                    _errorFlow.trySend(message)
                    message
                }
            ) {
                _successFlow.trySend(managerUtils.getString(R.string.password_success))
            }
        }
    }

    private fun onSelectNextItem() {
        _selectorFlow.trySend(SelectorRecover.NextPage)
    }

    fun onPressBack(currentPage: Int) {
        if (currentPage == 0) {
            _selectorFlow.trySend(SelectorRecover.Exit)
            return
        }

        _selectorFlow.trySend(SelectorRecover.PrevPage)
    }

    sealed class SelectorRecover {
        object Exit : SelectorRecover()
        object PrevPage : SelectorRecover()
        object NextPage : SelectorRecover()
    }
}