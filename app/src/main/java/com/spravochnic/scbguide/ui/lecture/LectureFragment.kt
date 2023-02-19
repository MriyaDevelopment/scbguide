package com.spravochnic.scbguide.ui.lecture

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.isDigitsOnly
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.spravochnic.scbguide.databinding.FragmentLectureBinding
import com.spravochnic.scbguide.ui.lecture.adapter.LecturyAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LectureFragment: Fragment() {

    private var _binding: FragmentLectureBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel by viewModels<LectureViewModel>()
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
        setAdapters()
        setListeners()
        setObservable()
    }

    private fun setAdapters() {
        binding.rvLecture.adapter = adapter
    }

    //Отдаем с view (fragment)
    private fun setListeners() = with(binding) {
        adapter.setClickLectureActionListener {
            Log.d("LECTURE_CLICK_ITEM", "name $it")
        }
    }

    //Принимаем с viewModel
    private fun setObservable() = with(viewModel) {
        viewModelScope.launch {
            lecturesState.collect {
                adapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}