package com.spravochnic.scbguide.ui.lecture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.spravochnic.scbguide.adapters.LectureAdapter
import com.spravochnic.scbguide.common.base.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentLectureBinding
import com.spravochnic.scbguide.db.DataBase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class LectureFragment : BaseFragment() {

    private val viewModel: LectureViewModel by viewModels { viewModelFactory }
    private lateinit var binding: FragmentLectureBinding

    @Inject
    lateinit var db: DataBase

    private val lectureAdapter by lazy {
        LectureAdapter(contextUtils, onClickItemLecture = onClickItemLecture)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLectureBinding.inflate(inflater, container, false).apply {
        binding = this
        lectureViewModel = viewModel
        lifecycleOwner = viewLifecycleOwner
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservable()
        setAdapter()
    }

    private fun setAdapter() {
        binding.recyclerView.adapter = lectureAdapter
        binding.recyclerView.itemAnimator = null
    }

    private fun setObservable() {
        with(lifecycleScope) {
            launch {
                viewModel.lectureUIState.collect { state ->
                    when (state) {
                        is LectureState.LectureEmpty -> {
                            viewModel.loadLectureUIState()
                        }
                        is LectureState.LectureSuccess -> {
                            lectureAdapter.submitList(state.data)
                        }
                    }
                }
            }
            launchWhenStarted {
                viewModel.transitionLectureEvent.collect { transition ->
                    when (transition) {
                        TransactionLectureEvent.NAVIGATION -> {
                            popBackStackFragment()
                        }
                    }
                }
            }
        }
    }

    private val onClickItemLecture: (String) -> Unit = { type ->
        transitionFromFragmentToFragment(
            LectureFragmentDirections.actionLectureFragmentToDetailsLectureFragment(
                type
            )
        )
    }
}