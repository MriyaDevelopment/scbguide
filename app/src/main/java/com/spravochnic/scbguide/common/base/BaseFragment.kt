package com.spravochnic.scbguide.common.base

import android.content.Context
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
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.spravochnic.scbguide.App
import com.spravochnic.scbguide.R
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

    @Inject
    lateinit var instance: App

    fun setStatusBarColor(@ColorRes statusBarColor: Int) {
        (activity as MainActivity).setStatusBarColor(statusBarColor)
    }

    fun transitionFromFragmentToFragment(navDirections: NavDirections) {
        findNavController().navigate(navDirections)
    }

    fun popBackStackFragment() {
        findNavController().popBackStack()
    }

    fun showSnackBar(
        anchorView: View,
        textSnackBar: String = contextUtils.getString(R.string.messageFailure),
        buttonText: String? = null,
        onButtonClicked: (() -> Unit)? = null
    ) {
        context?.let { context ->
            snackBarCreate(
                anchorView = anchorView,
                textSnackBar = textSnackBar,
                context = context,
                buttonText = buttonText
            ) { onButtonClicked?.invoke() }
        }
    }

    private fun snackBarCreate(
        anchorView: View,
        context: Context,
        textSnackBar: String,
        buttonText: String? = null,
        onButtonClicked: (() -> Unit)? = null,
    ) {
        val snackbar = Snackbar.make(
            anchorView, textSnackBar,
            Snackbar.LENGTH_SHORT
        )
        if (buttonText != null) {
            snackbar.setAction(buttonText) {
                onButtonClicked?.invoke() ?: snackbar.dismiss()
            }
            snackbar.duration = Snackbar.LENGTH_INDEFINITE
        }
        val imm = instance.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive) imm.hideSoftInputFromWindow(anchorView.windowToken, 0)
        snackbar.setActionTextColor(contextUtils.getColor(context, R.color.white))
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(contextUtils.getColor(context, R.color.blue_6689E5))
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(contextUtils.getColor(context, R.color.white))
        textView.textSize = 15f
        snackbar.show()
    }
}