package com.spravochnic.scbguide.ui.lecture.details.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.spravochnic.scbguide.common.base.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentDetailLectureBinding

class DetailLectureFragment: BaseFragment() {

    private val viewModel: DetailLectureViewModel by viewModels { viewModelFactory }
    private lateinit var binding: FragmentDetailLectureBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDetailLectureBinding.inflate(inflater, container, false).apply {
        binding = this
        detailLectureViewModel = viewModel
        lifecycleOwner = viewLifecycleOwner
    }.root
}