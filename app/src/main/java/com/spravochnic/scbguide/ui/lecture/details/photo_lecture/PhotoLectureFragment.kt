package com.spravochnic.scbguide.ui.lecture.details.photo_lecture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.transition.TransitionInflater
import coil.load
import com.spravochnic.scbguide.common.base.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentPhotoLectureBinding
import com.spravochnic.scbguide.ui.lecture.details.TransactionDetailsLectureEvent
import kotlinx.coroutines.flow.collect

class PhotoLectureFragment : BaseFragment() {

    lateinit var binding: FragmentPhotoLectureBinding
    private val viewModel: PhotoLectureViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentPhotoLectureBinding.inflate(inflater, container, false).apply {
        val transition =
            context?.let {
                TransitionInflater.from(it).inflateTransition(android.R.transition.move)
            }
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setValue()
    }

    private fun setValue() {
        val arg = arguments?.getString(PHOTO_QUICK)
        binding.imgDevice.load(arg)
    }

    companion object {
        const val PHOTO_QUICK = "photo_quick"
        const val PHOTO_NAME = "photo_name"
    }
}