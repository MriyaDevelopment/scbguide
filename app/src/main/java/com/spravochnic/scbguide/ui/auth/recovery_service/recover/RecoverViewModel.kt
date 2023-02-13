package com.spravochnic.scbguide.ui.auth.recovery_service.recover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.repositories.AuthRepository
import com.spravochnic.scbguide.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecoverViewModel @Inject constructor(
    private val validatorUtils: ValidatorUtils
): ViewModel() {

    private val _emailFlow = MutableStateFlow("")
    val emailFlow = _emailFlow.asStateFlow()

    val validatorFlow = _emailFlow.map { email ->
        validatorUtils.validateEmail(email)
    }

    fun onChangeEmail(email: String) {
        _emailFlow.value = email
    }
}