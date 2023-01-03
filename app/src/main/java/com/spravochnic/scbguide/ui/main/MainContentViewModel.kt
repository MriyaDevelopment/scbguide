package com.spravochnic.scbguide.ui.main

import androidx.lifecycle.ViewModel
import com.spravochnic.scbguide.base.network.MutableUIStateFlow
import com.spravochnic.scbguide.repositories.MainRepository
import com.spravochnic.scbguide.ui.main.adapter.MainContentAdapter
import com.spravochnic.scbguide.utils.LogUtil
import com.spravochnic.scbguide.utils.asUIStateFlow
import com.spravochnic.scbguide.utils.emitRequest
import com.spravochnic.scbguide.utils.launchOnDefault
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainContentViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _mainFlow = MutableUIStateFlow<List<MainContentAdapter.ItemMainContent>>()
    val mainFlow = _mainFlow.asUIStateFlow()

    init {
        onSwipeRefresh()
    }

    fun onSwipeRefresh() {
        launchOnDefault {
            _mainFlow.emitRequest(mainRepository.getMainContent()) { mainResult ->
                val mainList = mutableListOf<MainContentAdapter.ItemMainContent>()
                mainList.add(MainContentAdapter.ItemMainContent.WrapBoard)
                mainResult.result.forEach {
                    mainList.add(
                        MainContentAdapter.ItemMainContent.WrapCategory(
                            it
                        )
                    )
                }
                mainList
            }
        }
    }
}