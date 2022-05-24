package com.spravochnic.scbguide.ui.main

import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import androidx.room.Database
import com.spravochnic.scbguide.BR
import com.spravochnic.scbguide.api.ApiNetwork
import com.spravochnic.scbguide.api.models.toLectureCategories
import com.spravochnic.scbguide.common.base.BaseViewModel
import com.spravochnic.scbguide.db.DataBase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val db: DataBase,
    private val apiNetwork: ApiNetwork
): BaseViewModel() {

    private val _mainUIState = MutableStateFlow<MainState>(MainState.Empty)
    val mainUIState = _mainUIState.asStateFlow()

    private val _transitionEvent = Channel<TransactionEvent>()
    val transitionEvent = _transitionEvent.receiveAsFlow()

    fun loadLectureData() {
        viewModelScope.launch {
            _mainUIState.value = MainState.LoadingMainState
            try {
                val response = apiNetwork.getCategoriesLectureAsync().await()
                if (response.result == "success") {
                    val lectureList = response.categories.toLectureCategories()
                    db.dataBaseDao().setLectureList(lectureList)
                    _mainUIState.value = MainState.SuccessMainState(lectureList)
                } else {
                    _mainUIState.value = MainState.ErrorMainState(errorMessage = response.error)
                }
            }catch (e: Exception) {
                _mainUIState.value = MainState.FailureMainState(failure = e.message)
            }
        }
    }

    fun onClickTransaction(transaction: TransactionEvent) = viewModelScope.launch {
        _transitionEvent.send(transaction)
    }

}