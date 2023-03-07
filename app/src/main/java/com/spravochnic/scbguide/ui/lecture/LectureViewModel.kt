package com.spravochnic.scbguide.ui.lecture

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.parcelize.Parcelize

class LectureViewModel: ViewModel() {

    private val _lecturesState = MutableStateFlow<List<Lecture>>(emptyList())
    val lecturesState =_lecturesState.asStateFlow()

    init {
        setList()
    }

    private fun setList() {
        val list = mutableListOf<Lecture>()

        repeat(20) {
            list.add(Lecture(it, "$it name", ""))
        }

        _lecturesState.value = list
    }

    data class Lecture(
        val id: Int,
        val name: String,
        val image: String,
    )



}