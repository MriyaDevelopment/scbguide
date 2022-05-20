package com.spravochnic.scbguide.ui.test.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.spravochnic.scbguide.common.base.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentDetailsTestBinding

class DetailsTestFragment : BaseFragment() {

    private val viewModel: DetailsTestViewModel by viewModels { viewModelFactory }
    private lateinit var binding: FragmentDetailsTestBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDetailsTestBinding.inflate(inflater, container, false).apply {
        binding = this
        detailsTestViewModel = viewModel
        lifecycleOwner = viewLifecycleOwner
    }.root
}