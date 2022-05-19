package com.spravochnic.scbguide.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.spravochnic.scbguide.common.base.BaseFragment
import com.spravochnic.scbguide.databinding.DialogResultBinding

class ResultDialogFragment: BaseFragment() {

    private val viewModel: ResultViewModel by viewModels { viewModelFactory }
    private lateinit var binding: DialogResultBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DialogResultBinding.inflate(inflater, container, false).apply {
        binding = this
        resultViewModel = viewModel
        lifecycleOwner = viewLifecycleOwner
    }.root
}