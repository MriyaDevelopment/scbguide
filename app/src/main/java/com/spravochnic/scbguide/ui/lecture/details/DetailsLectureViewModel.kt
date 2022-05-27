package com.spravochnic.scbguide.ui.lecture.details

import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.spravochnic.scbguide.BR
import com.spravochnic.scbguide.common.base.BaseViewModel
import com.spravochnic.scbguide.db.DataBase
import com.spravochnic.scbguide.db.entity.LectureEntity
import com.spravochnic.scbguide.ui.lecture.TransactionLectureEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsLectureViewModel @Inject constructor(
    val db: DataBase
) : BaseViewModel() {

    private val _lectureUIState = MutableStateFlow<DetailsLectureState>(DetailsLectureState.Empty)
    val lectureUIState = _lectureUIState.asStateFlow()

    private val _transitionDetailsLectureEvent = Channel<TransactionDetailsLectureEvent>()
    val transitionDetailsLectureEvent = _transitionDetailsLectureEvent.receiveAsFlow()

    fun setValue(type: String) = viewModelScope.launch {
        val lectureList = db.dataBaseDao().getLectureList()
        val listType = mutableListOf<LectureEntity>()
        lectureList.let { lecture ->
            for (i in lecture.indices) {
                if (lecture[i].type == type) {
                    listType.add(lecture[i])
                }
            }
        }
        _lectureUIState.value = DetailsLectureState.setDataLecture(listType)
    }

    fun onClickTransaction(transaction: TransactionDetailsLectureEvent) = viewModelScope.launch {
        _transitionDetailsLectureEvent.send(transaction)
    }
}