package com.spravochnic.scbguide.ui.test

import androidx.lifecycle.viewModelScope
import com.spravochnic.scbguide.api.models.testCategories
import com.spravochnic.scbguide.common.base.BaseViewModel
import com.spravochnic.scbguide.ui.main.TransactionEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TestViewModel @Inject constructor() : BaseViewModel() {

    private val _testUIState = MutableStateFlow<TestState>(TestState.Empty)
    val testUIState = _testUIState.asStateFlow()

    private val _transitionTestEvent = Channel<TransactionTestEvent>()
    val transitionTestEvent = _transitionTestEvent.receiveAsFlow()

    fun loadTestData() {
        _testUIState.value = TestState.setDataTest(testCategories())
    }

    fun onClickTransaction(transaction: TransactionTestEvent) = viewModelScope.launch {
        _transitionTestEvent.send(transaction)
    }
}