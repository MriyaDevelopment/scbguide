package com.spravochnic.scbguide.ui.auth.recovery_service

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.spravochnic.scbguide.base.ui.adapter.BasePagerAdapter
import com.spravochnic.scbguide.base.ui.fragments.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentServiceRecoverBinding
import com.spravochnic.scbguide.ui.auth.recovery_service.code.CodeFragment
import com.spravochnic.scbguide.ui.auth.recovery_service.password.PasswordFragment
import com.spravochnic.scbguide.ui.auth.recovery_service.recover.RecoverFragment
import com.spravochnic.scbguide.utils.observe
import com.spravochnic.scbguide.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecoverServiceFragment :
    BaseFragment<FragmentServiceRecoverBinding>(FragmentServiceRecoverBinding::inflate) {

    private val viewModel by viewModels<RecoverServiceViewModel>()

    private val adapter by lazy {
        BasePagerAdapter(
            arrayOf(
                RecoverFragment(),
                CodeFragment(),
                PasswordFragment()
            ),
            childFragmentManager,
            viewLifecycleOwner.lifecycle
        )
    }

    override fun onBackPressed() {
        viewModel.onPressBack(binding.vpServiceRecover.currentItem)
    }

    override fun setListeners() = with(binding) {
        tbServiceRecover.setNavigationOnClickListener {
            viewModel.onPressBack(binding.vpServiceRecover.currentItem)
        }
    }

    override fun setAdapter() = with(binding) {
        vpServiceRecover.adapter = adapter
        vpServiceRecover.offscreenPageLimit = adapter.itemCount
        vpServiceRecover.isUserInputEnabled = false
        cpiServiceRecover.attachToViewPager(vpServiceRecover)
    }

    override fun setObservable() = with(viewModel) {
        errorFlow.observe(viewLifecycleOwner) {
            showSnackBar(it, binding.root)
        }

        successFlow.observe(viewLifecycleOwner) { message ->
            setFragmentResult(MESSAGE, bundleOf(MESSAGE to message))
            navigationController.popBackStack()
        }

        selectorFlow.observe(viewLifecycleOwner) { result ->
            when (result) {
                is RecoverServiceViewModel.SelectorRecover.PrevPage -> {
                    binding.vpServiceRecover.also { it.setCurrentItem(it.currentItem - 1, true) }
                }
                is RecoverServiceViewModel.SelectorRecover.Exit -> {
                    super.onBackPressed()
                }
                is RecoverServiceViewModel.SelectorRecover.NextPage -> {
                    binding.vpServiceRecover.also { it.setCurrentItem(it.currentItem + 1, true) }
                }
            }
        }
    }

    companion object {
        const val MESSAGE = "MESSAGE"
    }
}