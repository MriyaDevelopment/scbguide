package com.spravochnic.scbguide.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.databinding.ActivityMainBinding.inflate
import com.spravochnic.scbguide.databinding.DialogErrorBinding
import com.spravochnic.scbguide.ui.main.MainActivity
import com.spravochnic.scbguide.utils.ContextUtils
import dagger.android.support.DaggerFragment
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


//    fun showErrorDialog(
//        errorMessage: String,
//        actionOnRepeat: (() -> Unit)?,
//        actionOnCancel: (() -> Unit)?
//    ) {
//        (activity as MainActivity).showErrorDialog(
//            errorMessage = errorMessage,
//            { actionOnRepeat?.invoke() },
//            { actionOnCancel?.invoke() })
//    }
//    fun showErrorDialog(errorMessage: String, actionOnRepeat: (() -> Unit)?, actionOnCancel: (() -> Unit)?) {
//        val bottomSheetDialog = context?.let { BottomSheetDialog(it, R.style.BottomSheetDialog) }
//        val binding = DataBindingUtil.inflate<DialogErrorBinding>(
//            layoutInflater,
//            R.layout.dialog_error,
//            null,
//            true
//        )
//        binding.textError.text = errorMessage
//        binding.repeat.setOnClickListener {
//            actionOnRepeat?.invoke()
//        }
//        binding.cancel.setOnClickListener {
//            actionOnCancel?.invoke()
//        }
//        bottomSheetDialog?.setOnCancelListener {
//            actionOnCancel?.invoke()
//        }
//        bottomSheetDialog?.setContentView(binding.root)
//        bottomSheetDialog?.show()
//    }
}