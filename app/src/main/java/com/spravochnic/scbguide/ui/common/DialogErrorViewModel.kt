package com.spravochnic.scbguide.ui.common

import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.spravochnic.scbguide.BR
import com.spravochnic.scbguide.common.base.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DialogErrorViewModel @Inject constructor(): BaseViewModel() {

    @get:Bindable
    var errorMessage: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.errorMessage)
        }

    private val _errorEvent = Channel<ErrorTransactionEvent> {  }
    val errorEvent = _errorEvent.receiveAsFlow()

    fun onClickDialog(event: ErrorTransactionEvent) = viewModelScope.launch {
        _errorEvent.send(event)
    }
}