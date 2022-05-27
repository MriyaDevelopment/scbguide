package com.spravochnic.scbguide.ui.lecture.details

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
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.adapters.LectureCarouselAdapter
import com.spravochnic.scbguide.common.base.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentDetailsLectureBinding
import com.spravochnic.scbguide.ui.lecture.details.photo_lecture.PhotoLectureFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class DetailsLectureFragment : BaseFragment() {

    private val viewModel: DetailsLectureViewModel by viewModels { viewModelFactory }
    private lateinit var binding: FragmentDetailsLectureBinding
    private val args: DetailsLectureFragmentArgs by navArgs()

    @Inject
    lateinit var baseUrl: String

    private val lectureCarouselAdapter by lazy {
        LectureCarouselAdapter(
            baseUrl,
            contextUtils,
            onClickWatchFull = onClickWatchFull,
            onClickImgDevice = onClickImgDevice
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDetailsLectureBinding.inflate(inflater, container, false).apply {
        binding = this
        detailsLectureViewModel = viewModel
        lifecycleOwner = viewLifecycleOwner
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitleToolbar()
        setObservable()
        initAdapter()
    }

    private fun initAdapter() {
        binding.run {
            val snapHelper: LinearSnapHelper = object : LinearSnapHelper() {
                override fun findTargetSnapPosition(
                    layoutManager: RecyclerView.LayoutManager,
                    velocityX: Int,
                    velocityY: Int
                ): Int {
                    val centerView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
                    val position = layoutManager.getPosition(centerView)
                    var targetPosition = -1
                    if (layoutManager.canScrollHorizontally()) {
                        targetPosition = if (velocityX < 0) {
                            position - 1
                        } else {
                            position + 1
                        }
                    }
                    if (layoutManager.canScrollVertically()) {
                        targetPosition = if (velocityY < 0) {
                            position - 1
                        } else {
                            position + 1
                        }
                    }
                    val firstItem = 0
                    val lastItem = layoutManager.itemCount - 1
                    targetPosition = lastItem.coerceAtMost(targetPosition.coerceAtLeast(firstItem))
                    return targetPosition
                }
            }
            snapHelper.attachToRecyclerView(recyclerView)
            recyclerView.adapter = lectureCarouselAdapter
            postponeEnterTransition()
            recyclerView.post { startPostponedEnterTransition() }
        }
    }

    private fun setObservable() {
        with(lifecycleScope) {
            launch {
                viewModel.lectureUIState.collect { state ->
                    when (state) {
                        is DetailsLectureState.Empty -> {
                            viewModel.setValue(args.type)
                        }
                        is DetailsLectureState.setDataLecture -> {
                            lectureCarouselAdapter.submitList(state.data)
                        }
                    }
                }
            }
            launchWhenStarted {
                viewModel.transitionDetailsLectureEvent.collect { event ->
                    when (event) {
                        TransactionDetailsLectureEvent.NAVIGATION -> {
                            popBackStackFragment()
                        }
                    }
                }
            }
        }
    }

    private fun setTitleToolbar() {
        binding.toolbar.title = args.name
    }

    private val onClickWatchFull: (String, String) -> Unit = { type, name ->
        findNavController().navigate(
            DetailsLectureFragmentDirections.actionDetailsLectureFragmentToDetailLectureFragment(
                type = type,
                name = name
            )
        )
    }

    private val onClickImgDevice: (String, ImageView, String) -> Unit = { image, imageView: ImageView, name ->
        val transitionExtras = FragmentNavigatorExtras(
            imageView to "quick_image_transition",
        )
        val bundle = Bundle()
        bundle.putString(PhotoLectureFragment.PHOTO_NAME, name)
        bundle.putString(PhotoLectureFragment.PHOTO_QUICK, image)
        findNavController().navigate(
            R.id.action_detailsLectureFragment_to_photoLectureFragment,
            bundle,
            null,
            transitionExtras
        )
    }
}