package com.spravochnic.scbguide.base.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.addCallback
import androidx.annotation.CallSuper
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import androidx.viewbinding.ViewBinding
import com.spravochnic.scbguide.NavigationViewModel
import com.spravochnic.scbguide.databinding.ViewStateHandlerBinding
import com.spravochnic.scbguide.utils.gone
import com.spravochnic.scbguide.utils.invisible
import com.spravochnic.scbguide.utils.visible

abstract class BaseFragment<VB : ViewBinding>(
    private val vbInflate: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    protected open val isBnvVisible = true

    protected val navViewModel by activityViewModels<NavigationViewModel>()

    protected var viewStateHandler: ViewStateHandlerBinding? = null
        private set

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!

    protected open val navigationController by lazy { findNavController() }

    protected val windowInsetsController by lazy {
        WindowInsetsControllerCompat(
            requireActivity().window,
            binding.root
        )
    }

    protected open fun onCreateView() {}
    protected open fun initToolbar() {}
    protected open fun setListeners() {}
    protected open fun setObservable() {}
    protected open fun setAdapter() {}

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val transition =
            TransitionInflater
                .from(requireContext())
                .inflateTransition(android.R.transition.move)
                .setDuration(250L)

        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
        _binding = vbInflate(inflater, container, false)
        viewStateHandler = ViewStateHandlerBinding.inflate(layoutInflater)
        onCreateView()
        return FrameLayout(requireContext()).apply {
            addView(binding.root)
            viewStateHandler?.root?.let(::addView)
        }
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initOnBackPressedDispatcher()
        setBnvVisible()
        setListeners()
        setObservable()
        setAdapter()
    }

    protected open fun initOnBackPressedDispatcher() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            onBackPressed()
        }
    }

    protected fun setPreLoader(isVisible: Boolean) {
        viewStateHandler?.run {
            flProgressStateHandler.isVisible = isVisible
            flErrorStateHandler.gone()
        }
    }

    protected inline fun setError(
        errorText: String = "Ошибка! Попробуйте еще раз!",
        isRootInvisible: Boolean = true,
        crossinline action: () -> Unit
    ) {
        binding.root.visibility = if(isRootInvisible) View.INVISIBLE else View.VISIBLE
        viewStateHandler?.run {
            flErrorStateHandler.visible()
            flProgressStateHandler.gone()
            txtErrorViewStateHandler.text = errorText
            btnUpdateViewStateHandler.setOnClickListener {
                action.invoke()
            }
        }
    }

    protected fun setSuccess() {
        binding.root.visible()
        viewStateHandler?.run {
            flErrorStateHandler.gone()
            flProgressStateHandler.gone()
        }
    }

    protected fun setLoading() {
        binding.root.invisible()
        viewStateHandler?.run {
            flErrorStateHandler.gone()
            flProgressStateHandler.visible()
        }
    }

    private fun setBnvVisible() {
        navViewModel.onChangeBnvVisible(isBnvVisible)
    }

    protected open fun onBackPressed() {
        navigationController.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}