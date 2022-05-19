package com.spravochnic.scbguide.ui.lecture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.spravochnic.scbguide.common.base.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentLectureBinding

class LectureFragment : BaseFragment() {

    private val viewModel: LectureViewModel by viewModels { viewModelFactory }
    private lateinit var binding: FragmentLectureBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLectureBinding.inflate(inflater, container, false).apply {
        binding = this
        lectureViewModel = viewModel
        lifecycleOwner = viewLifecycleOwner
    }.root
}