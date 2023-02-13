package com.spravochnic.scbguide.ui.auth.recovery_service

import androidx.lifecycle.ViewModel
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.repositories.AuthRepository
import com.spravochnic.scbguide.utils.ManagerUtils
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

    private val _emailFlow = MutableStateFlow("")
    val emailFlow = _emailFlow.asStateFlow()

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

        }
    }

    fun onClickCode(code: String) {
        launchOnDefault {

        }
    }

    fun onClickChangePass(password: String) {
        launchOnDefault {

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