package com.spravochnic.scbguide.ui.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.spravochnic.scbguide.common.base.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentTestBinding

class TestFragment : BaseFragment() {

    private val viewModel: TestViewModel by viewModels { viewModelFactory }
    private lateinit var binding: FragmentTestBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentTestBinding.inflate(inflater, container, false).apply {
        binding = this
        testViewModel = viewModel
        lifecycleOwner = viewLifecycleOwner
    }.root
}