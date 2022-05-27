package com.spravochnic.scbguide.ui.lecture.details.detail

import android.provider.ContactsContract
import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.spravochnic.scbguide.BR
import com.spravochnic.scbguide.common.base.BaseViewModel
import com.spravochnic.scbguide.db.DataBase
import com.spravochnic.scbguide.db.entity.LectureEntity
import com.spravochnic.scbguide.ui.lecture.details.DetailState
import com.spravochnic.scbguide.ui.lecture.details.TransactionDetailsLectureEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailLectureViewModel @Inject constructor(
    val db: DataBase
) : BaseViewModel() {

    private val _detailUIState = MutableStateFlow<DetailState>(DetailState.Empty)
    val detailUIState = _detailUIState.asStateFlow()

    private val _transitionDetailLectureEvent = Channel<TransactionDetailsLectureEvent>()
    val transitionDetailLectureEvent = _transitionDetailLectureEvent.receiveAsFlow()

    fun setValue(name: String) = viewModelScope.launch {
        db.dataBaseDao().getLectureList().let { listLecture ->
            for (i in listLecture.indices) {
                if (listLecture[i].name == name) {
                    _detailUIState.value = DetailState.setDataDetail(listLecture[i])
                }
            }
        }
    }

    fun onClickTransaction(transaction: TransactionDetailsLectureEvent) = viewModelScope.launch {
        _transitionDetailLectureEvent.send(transaction)
    }

}