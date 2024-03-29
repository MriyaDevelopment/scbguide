package com.spravochnic.scbguide.base.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.spravochnic.scbguide.R

abstract class BaseBSDialogFragment<VB : ViewBinding>(
    private val vbInflate: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : BottomSheetDialogFragment() {

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!

    protected val navigationController by lazy { findNavController() }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = vbInflate(inflater, container, false)
        onCreateView()
        return binding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setObservable()
        setAdapters()
    }

    @Deprecated("use onViewCreated")
    protected open fun onCreateView() {}
    protected open fun setObservable() {}
    protected open fun setListeners() {}
    protected open fun setAdapters() {}

    @CallSuper
    protected open fun setSettingsDialog(dialog: BottomSheetDialog) {
        with(dialog) {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.skipCollapsed = true
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.attributes?.windowAnimations = R.style.BottomSheetDialogAnimation
        dialog.setOnShowListener {
            setSettingsDialog((it as BottomSheetDialog))
        }
        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}