package com.spravochnic.scbguide.ui.lecture.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.spravochnic.scbguide.common.base.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentDetailsLectureBinding

class DetailsLectureFragment : BaseFragment() {

    private val viewModel: DetailsLectureViewModel by viewModels { viewModelFactory }
    private lateinit var binding: FragmentDetailsLectureBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDetailsLectureBinding.inflate(inflater, container, false).apply {
        binding = this
        detailsLectureViewModel = viewModel
        lifecycleOwner = viewLifecycleOwner
    }.root
}