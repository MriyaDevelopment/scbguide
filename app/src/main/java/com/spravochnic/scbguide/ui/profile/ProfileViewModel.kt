package com.spravochnic.scbguide.ui.profile

import androidx.lifecycle.ViewModel
import com.spravochnic.scbguide.ui.profile.adapter.ProfileAdapter
import com.spravochnic.scbguide.utils.LogUtil
import com.spravochnic.scbguide.utils.enums.ProfileType
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(): ViewModel() {

    private val _profileFlow = MutableStateFlow<List<ProfileAdapter.ItemProfile>>(emptyList())
    val profileFlow = _profileFlow.asStateFlow()

}