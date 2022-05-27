package com.spravochnic.scbguide.ui.main

import androidx.lifecycle.viewModelScope
import com.spravochnic.scbguide.api.ApiNetwork
import com.spravochnic.scbguide.api.models.toCategoriesLecture
import com.spravochnic.scbguide.api.models.toLectureCategories
import com.spravochnic.scbguide.api.models.toTestQuestion
import com.spravochnic.scbguide.common.base.BaseViewModel
import com.spravochnic.scbguide.db.DataBase
import com.spravochnic.scbguide.prefs.GuidePrefs
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val db: DataBase,
    private val apiNetwork: ApiNetwork,
) : BaseViewModel() {

    private val _mainUIState = MutableStateFlow<MainState>(MainState.Empty)
    val mainUIState = _mainUIState.asStateFlow()

    private val _transitionEvent = Channel<TransactionEvent>()
    val transitionEvent = _transitionEvent.receiveAsFlow()

    private val mainPageLoadingMap = mutableMapOf<String, MainState>()

    private fun checkIsMainPageReady() {
        if (mainPageLoadingMap.containsValue(MainState.LoadingMainState)) return
        if (mainPageLoadingMap.containsValue(MainState.ErrorMainState)) {
            _mainUIState.value = MainState.ErrorMainState
            return
        }
        if (mainPageLoadingMap.containsValue(MainState.FailureMainState)) {
            _mainUIState.value = MainState.FailureMainState
            return
        }
        _mainUIState.value = MainState.SuccessMainState
    }

    private fun loadLectureData() {
        viewModelScope.launch {
            mainPageLoadingMap[LECTURE] = MainState.LoadingMainState
            try {
                val response = apiNetwork.getCategoriesLectureAsync().await()
                val lectureList = response.categories?.toCategoriesLecture()
                if (response.result == "success") {
                    lectureList?.let { list ->
                        db.dataBaseDao().setLectureList(list)
                    }
                    mainPageLoadingMap[LECTURE] = MainState.SuccessMainState
                } else {
                    mainPageLoadingMap[LECTURE] = MainState.ErrorMainState
                    _mainUIState.value = MainState.ErrorLectureMainState(response.error.toString())
                }
            } catch (e: Exception) {
                mainPageLoadingMap[LECTURE] = MainState.FailureMainState
            }
            checkIsMainPageReady()
        }
    }

    private fun loadCategoriesLecture() {
        viewModelScope.launch {
            mainPageLoadingMap[CATEGORIES_LECTURE] = MainState.LoadingMainState
            try {
                val response = apiNetwork.getLectureCategoryAsync().await()
                val lectureCategories = response.lecture?.toLectureCategories()
                if (response.result == "success") {
                    lectureCategories?.let { list ->
                        db.dataBaseDao().setLectureCategoriesList(list)
                    }
                    mainPageLoadingMap[CATEGORIES_LECTURE] = MainState.SuccessMainState
                } else {
                    mainPageLoadingMap[CATEGORIES_LECTURE] = MainState.ErrorMainState
                    _mainUIState.value =
                        MainState.ErrorCategoriesLectureMainState(response.error.toString())
                }
            } catch (e: Exception) {
                mainPageLoadingMap[CATEGORIES_LECTURE] = MainState.FailureMainState
            }
            checkIsMainPageReady()
        }
    }

    private fun loadTestQuestionData() {
        viewModelScope.launch {
            mainPageLoadingMap[TEST] = MainState.LoadingMainState
            try {
                val response = apiNetwork.getTestQuestionsAsync().await()
                val questList = response.questList?.toTestQuestion()
                if (response.result == "success") {
                    questList?.let { list ->
                        db.dataBaseDao().setTestQuestionList(list)
                    }
                    mainPageLoadingMap[TEST] = MainState.SuccessMainState
                } else {
                    mainPageLoadingMap[TEST] = MainState.ErrorMainState
                    _mainUIState.value = MainState.ErrorTestMainState(response.error.toString())
                }
            } catch (e: Exception) {
                mainPageLoadingMap[TEST] = MainState.FailureMainState
            }
            checkIsMainPageReady()
        }
    }

    fun onClickTransaction(transaction: TransactionEvent) = viewModelScope.launch {
        _transitionEvent.send(transaction)
    }

    fun loadData() = viewModelScope.launch {
        resetMainPageLoadingMap()
        loadTestQuestionData()
        loadLectureData()
        loadCategoriesLecture()
    }

    fun updateDate() = viewModelScope.launch {
        resetMainPageLoadingMap()
        if (db.dataBaseDao().getTestQuestionList().isEmpty()) {
            loadTestQuestionData()
        } else {
            mainPageLoadingMap[TEST] = MainState.SuccessMainState
            checkIsMainPageReady()
        }
        if (db.dataBaseDao().getLectureList().isEmpty()) {
            loadLectureData()
        } else {
            mainPageLoadingMap[LECTURE] = MainState.SuccessMainState
            checkIsMainPageReady()
        }
        if (db.dataBaseDao().getLectureCategoriesList().isEmpty()) {
            loadCategoriesLecture()
        } else {
            mainPageLoadingMap[CATEGORIES_LECTURE] = MainState.SuccessMainState
            checkIsMainPageReady()
        }
    }

    private fun resetMainPageLoadingMap() {
        _mainUIState.value = MainState.LoadingMainState
        mainPageLoadingMap.run {
            clear()
        }
    }

    companion object {
        const val CATEGORIES_LECTURE = "CATEGORIES_LECTURE"
        const val LECTURE = "LECTURE"
        const val TEST = "TEST"
    }

}