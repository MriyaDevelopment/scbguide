package com.spravochnic.scbguide.ui.auth.recovery_service.code

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.utils.ManagerUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CodeViewModel @Inject constructor(
    private val managerUtils: ManagerUtils
) : ViewModel() {

    private val firstCodeFlow = MutableStateFlow("")
    private val secondCodeFlow = MutableStateFlow("")
    private val thirdCodeFlow = MutableStateFlow("")
    private val fourthCodeFlow = MutableStateFlow("")

    var code = ""
        private set

    private var timer: Job? = null

    private val newCode = managerUtils.getString(R.string.recover_new_code)

    private val _timerFlow = MutableStateFlow<TimerResult>(TimerResult.DropTimer(newCode))
    val timerFlow = _timerFlow.asStateFlow()

    val validator = combine(
        firstCodeFlow,
        secondCodeFlow,
        thirdCodeFlow,
        fourthCodeFlow
    ) { first, second, third, fourth ->
        code = "${first}${second}${third}${fourth}"
        code.length == LENGTH_CODE
    }

    fun startTimer() {
        dropTimer()
        timer = viewModelScope.launch(Dispatchers.Unconfined) {
            repeat(ONE_MINUTE_SECOND + 1) {
                val tick = ONE_MINUTE_SECOND - it
                _timerFlow.value = TimerResult.StartTimer(managerUtils.getString(
                    R.string.recover_new_code_time,
                    "0:${if (tick < 10) "0${tick}" else tick}"
                ))
                if (tick == 0) dropTimer()
                delay(SECOND_MILLIS)
            }
        }
    }

    private fun dropTimer() {
        timer?.cancel()
        _timerFlow.value = TimerResult.DropTimer(newCode)
    }

    fun onChangeFirstCode(firstCode: String) {
        firstCodeFlow.value = firstCode
    }

    fun onChangeSecondCode(secondCode: String) {
        secondCodeFlow.value = secondCode
    }

    fun onChangeThirdCode(thirdCode: String) {
        thirdCodeFlow.value = thirdCode
    }

    fun onChangeFourthCode(fourthCode: String) {
        fourthCodeFlow.value = fourthCode
    }

    sealed class TimerResult {
        data class StartTimer(val result: String): TimerResult()
        data class DropTimer(val result: String): TimerResult()
    }

    companion object {
        private const val ONE_MINUTE_SECOND = 60
        private const val SECOND_MILLIS = 1_000L
        private const val LENGTH_CODE = 4
    }
}