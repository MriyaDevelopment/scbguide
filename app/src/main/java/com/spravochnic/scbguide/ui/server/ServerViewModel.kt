package com.spravochnic.scbguide.ui.server

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spravochnic.scbguide.App
import com.spravochnic.scbguide.ui.api.ApiService
import com.spravochnic.scbguide.ui.api.models.CharacterResponse
import com.spravochnic.scbguide.ui.lecture.LectureViewModel
import kotlinx.coroutines.launch

class ServerViewModel: ViewModel() {

    val results: MutableLiveData<List<LectureViewModel.Lecture>> by lazy {
        MutableLiveData<List<LectureViewModel.Lecture>>()
    }

    private var checkId: Int? = null

    init {
        getCharacter()
    }

    fun getCharacter() {
        val list = mutableListOf<LectureViewModel.Lecture>()
        repeat(20) {
            list.add(LectureViewModel.Lecture(it, "Name ${it}"))
        }
        results.value = list
    }

    fun removeIndex() {
        results.value = results.value?.toMutableList()?.apply {
            val result = indexOfFirst { it.id == checkId }
            removeAt(result)
        }
    }

    fun addElement() {
        results.value = results.value?.toMutableList()?.apply {
            val result = firstOrNull {it.id == checkId} ?: LectureViewModel.Lecture(0, "12323132")
            add(result)
        }
    }

    fun checkElement(id: Int) {
        results.value = results.value?.map { it.copy(check = it.id == id) }
        checkId = id
    }
}