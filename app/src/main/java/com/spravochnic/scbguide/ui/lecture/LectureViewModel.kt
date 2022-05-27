package com.spravochnic.scbguide.ui.lecture

import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.spravochnic.scbguide.BR
import com.spravochnic.scbguide.common.base.BaseViewModel
import com.spravochnic.scbguide.db.DataBase
import com.spravochnic.scbguide.db.entity.LectureCategoriesEntity
import com.spravochnic.scbguide.ui.main.TransactionEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LectureViewModel @Inject constructor(private val db: DataBase) : BaseViewModel() {

    private val _lectureUIState = MutableStateFlow<LectureState>(LectureState.LectureEmpty)
    val lectureUIState = _lectureUIState.asStateFlow()

    private val _transitionLectureEvent = Channel<TransactionLectureEvent>()
    val transitionLectureEvent = _transitionLectureEvent.receiveAsFlow()

    fun loadLectureUIState() {
        viewModelScope.launch {
            _lectureUIState.value = LectureState.LectureSuccess(db.dataBaseDao().getLectureCategoriesList())
        }
    }

    @get:Bindable
    var searchField: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.searchField)
            searchLectureField()
        }

    private fun searchLectureField() {
        viewModelScope.launch {
            val field = db.dataBaseDao().filtered(searchField)
            println(field)
        }
    }

    fun onClickTransaction(transactionLectureEvent: TransactionLectureEvent) = viewModelScope.launch {
        _transitionLectureEvent.send(transactionLectureEvent)
    }
}