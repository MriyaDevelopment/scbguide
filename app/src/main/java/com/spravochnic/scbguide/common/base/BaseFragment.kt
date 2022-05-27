package com.spravochnic.scbguide.common.base

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.spravochnic.scbguide.ui.main.MainActivity
import com.spravochnic.scbguide.utils.ContextUtils
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.newFixedThreadPoolContext
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var contextUtils: ContextUtils

    fun setStatusBarColor(@ColorRes statusBarColor: Int) {
        (activity as MainActivity).setStatusBarColor(statusBarColor)
    }

    fun transitionFromFragmentToFragment(navDirections: NavDirections) {
        findNavController().navigate(navDirections)
    }

    fun popBackStackFragment() {
        findNavController().popBackStack()
    }
}