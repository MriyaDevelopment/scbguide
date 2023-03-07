package com.spravochnic.scbguide.ui.server

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spravochnic.scbguide.App
import com.spravochnic.scbguide.ui.api.ApiService
import com.spravochnic.scbguide.ui.api.models.CharacterResponse
import kotlinx.coroutines.launch

class ServerViewModel: ViewModel() {

    val results: MutableLiveData<List<CharacterResponse.Result>> by lazy {
        MutableLiveData<List<CharacterResponse.Result>>()
    }

    init {
        getCharacter()
    }

    fun getCharacter(page: Int = 1) {
        viewModelScope.launch {
            try {
                val response = ApiService.API.getCharacter(1)
                if (response.isSuccessful) {
                    results.value = response.body()?.results

                } else {

                }

            } catch (e: Exception) {

            }
        }


    }
}