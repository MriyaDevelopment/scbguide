package com.spravochnic.scbguide.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun ViewModel.launchOnDefault(
    block: suspend CoroutineScope.() -> Unit
): Job {
    return viewModelScope.launch(Dispatchers.Default, block = block)
}