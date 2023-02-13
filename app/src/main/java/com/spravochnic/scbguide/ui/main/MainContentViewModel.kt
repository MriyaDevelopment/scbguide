package com.spravochnic.scbguide.ui.main

import androidx.lifecycle.ViewModel
import com.spravochnic.scbguide.repositories.MainRepository
import com.spravochnic.scbguide.ui.main.adapter.MainContentAdapter
import com.spravochnic.scbguide.utils.launchOnDefault
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainContentViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    init {
        onSwipeRefresh()
    }

    fun onSwipeRefresh() {
        launchOnDefault {

        }
    }
}