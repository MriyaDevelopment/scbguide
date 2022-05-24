package com.spravochnic.scbguide.ui.common

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.spravochnic.scbguide.common.base.BaseDialogFragment
import com.spravochnic.scbguide.databinding.DialogErrorBinding
import com.spravochnic.scbguide.ui.main.TransactionEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DialogErrorFragment : BaseDialogFragment() {

    lateinit var binding: DialogErrorBinding
    private val viewModel: DialogErrorViewModel by viewModels { viewModelFactory }
    private val args: DialogErrorFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DialogErrorBinding.inflate(inflater, container, false).apply {
        binding = this
        dialogErrorViewModel = viewModel
        lifecycleOwner = viewLifecycleOwner
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setValue()
        setObservable()
        dialog?.setOnCancelListener {
            popBackStackWithArg(ErrorTransactionEvent.CANCEL.name)
        }
    }

    private fun setValue() {
        viewModel.errorMessage = args.errorMessage
    }

    private fun setObservable() {
        lifecycleScope.launchWhenStarted {
            viewModel.errorEvent.collect { event ->
                popBackStackWithArg(event.name)
            }
        }
    }

    private fun popBackStackWithArg(args: String) {
        try {
            with(findNavController()) {
                previousBackStackEntry?.savedStateHandle?.set(RESULT_ERROR_DIALOG, args)
                popBackStack()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    companion object {
        const val RESULT_ERROR_DIALOG = "RESULT_ERROR_DIALOG"
    }
}