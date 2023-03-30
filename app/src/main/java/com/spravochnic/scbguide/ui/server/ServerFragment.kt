package com.spravochnic.scbguide.ui.server

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.spravochnic.scbguide.databinding.FragmentLectureBinding
import com.spravochnic.scbguide.databinding.FragmentServerBinding
import com.spravochnic.scbguide.ui.lecture.LectureViewModel
import com.spravochnic.scbguide.ui.lecture.adapter.LecturyAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ServerFragment : Fragment() {

    private var _binding: FragmentLectureBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel by viewModels<ServerViewModel>()
    private val adapter = LecturyAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLectureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvLecture.adapter = adapter

        viewModel.results.observe(viewLifecycleOwner) {

            adapter.submitList(
                it.map { result ->
                    LectureViewModel.Lecture(
                        id = result.id,
                        name = result.name,
                        image = result.image
                    )
                }
            )

        }
    }


}