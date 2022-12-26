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

    private val _profileFlow = MutableStateFlow(
        listOf(
            ProfileAdapter.ItemProfile(ProfileType.MY_LECTURES, bottom = false, top = true),
            ProfileAdapter.ItemProfile(ProfileType.MY_TESTS, bottom = true, top = false),
            ProfileAdapter.ItemProfile(ProfileType.PRIVACY_POLICY, bottom = false, top = true),
            ProfileAdapter.ItemProfile(ProfileType.TERMS_OF_SERVICE, bottom = true, top = false),
            ProfileAdapter.ItemProfile(ProfileType.EXIT, bottom = false, top = true),
            ProfileAdapter.ItemProfile(ProfileType.DELETE, bottom = true, top = false),
        )
    )
    val profileFlow = _profileFlow.asStateFlow()

}