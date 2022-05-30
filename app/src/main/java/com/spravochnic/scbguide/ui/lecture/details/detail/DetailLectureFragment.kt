package com.spravochnic.scbguide.ui.lecture.details.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.common.base.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentDetailLectureBinding
import com.spravochnic.scbguide.ui.lecture.details.DetailState
import com.spravochnic.scbguide.ui.lecture.details.TransactionDetailsLectureEvent
import com.spravochnic.scbguide.ui.lecture.details.photo_lecture.PhotoLectureFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailLectureFragment : BaseFragment() {

    private val viewModel: DetailLectureViewModel by viewModels { viewModelFactory }
    private lateinit var binding: FragmentDetailLectureBinding
    private val args: DetailLectureFragmentArgs by navArgs()

    @Inject
    lateinit var baseUrl: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDetailLectureBinding.inflate(inflater, container, false).apply {
        binding = this
        detailLectureViewModel = viewModel
        lifecycleOwner = viewLifecycleOwner
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservable()
    }

    private fun setObservable() {
        with(lifecycleScope) {
            launch {
                viewModel.detailUIState.collect { state ->
                    when (state) {
                        is DetailState.Empty -> {
                            viewModel.setValue(args.name)
                        }
                        is DetailState.setDataDetail -> {
                            binding.run {
                                val image = baseUrl + "instruments/imageLectory/" + state.data.image
                                imgLecture.load(image) {
                                    placeholder(R.drawable.img_placeholder)
                                }
                                imgLecture.transitionName = "transition_quick${state.data.id}"
                                description.text = state.data.description
                                name.text = state.data.name
                                imgLecture.setOnClickListener {
                                    transPhotoExtras(image, imgLecture)
                                }
                            }
                        }
                    }
                }
            }
            launchWhenStarted {
                viewModel.transitionDetailLectureEvent.collect { event ->
                    when (event) {
                        TransactionDetailsLectureEvent.NAVIGATION -> {
                            popBackStackFragment()
                        }
                    }
                }
            }
        }
    }

    private fun transPhotoExtras(image: String, imgLecture: ImageView) {
        val transitionExtras = FragmentNavigatorExtras(
            imgLecture to "quick_image_transition",
        )
        val bundle = Bundle()
        bundle.putString(PhotoLectureFragment.PHOTO_QUICK, image)
        findNavController().navigate(
            R.id.action_detailLectureFragment_to_photoLectureFragment,
            bundle,
            null,
            transitionExtras
        )
    }
}