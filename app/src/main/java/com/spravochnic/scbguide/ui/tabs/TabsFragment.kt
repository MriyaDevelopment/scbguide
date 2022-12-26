package com.spravochnic.scbguide.ui.tabs

import android.os.Bundle
import android.view.View
import androidx.core.view.*
import androidx.navigation.NavGraph
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.base.ui.fragments.BaseFragment
import com.spravochnic.scbguide.databinding.FragmentTabsBinding
import com.spravochnic.scbguide.utils.LogUtil
import com.spravochnic.scbguide.utils.dp
import com.spravochnic.scbguide.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabsFragment : BaseFragment<FragmentTabsBinding>(FragmentTabsBinding::inflate) {

    private val navHostFragment by lazy {
        childFragmentManager.findFragmentById(R.id.fcvTabs) as NavHostFragment
    }

    override val navigationController by lazy {
        navHostFragment.navController
    }

    override fun initOnBackPressedDispatcher() = Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomNavigation()
        binding.bnvTabsContainer.doOnLayout {
            navViewModel.heightBnvTab = it.height+10.dp.toInt()
        }
    }

    private fun initBottomNavigation() = with(binding) {
        /* Настраивает Multiple Back Stacks */
        bnvTabs.setupWithNavController(navigationController)

        /* Сбрасывает стэк экранов если нажали активную вкладку */
        bnvTabs.setOnItemReselectedListener {
            val option = NavOptions.Builder().setLaunchSingleTop(true).setPopUpTo(
                navigationController.graph.findStartDestination().id,
                inclusive = false,
                saveState = false
            ).build()

            runCatching {
                navigationController.navigate(it.itemId, null, option)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(bnvTabs) { v, insets ->
            v.updatePadding(bottom = 0)
            if (navViewModel.bnvVisible.value) {
                bnvTabs.isVisible = !insets.isVisible(WindowInsetsCompat.Type.ime())
            }
            insets
        }
    }

    override fun setObservable() = with(navViewModel) {
        bnvVisible.observe(viewLifecycleOwner) {
            binding.bnvTabs.isVisible = it
        }

        navigateToDeepLink.observe(viewLifecycleOwner) { tabDeepLink ->
            /* Переключается на нужную вкладку */
            binding.bnvTabs.selectedItemId = tabDeepLink.tabId

            /* Ищет граф текущей вкладки */
            val currentGraph = navigationController.graph.firstOrNull {
                it.id == tabDeepLink.tabId
            }

            (currentGraph as? NavGraph)?.let {
                /* Сбрасывает стэк вкладки до корневого экрана */
                val option = NavOptions.Builder().setLaunchSingleTop(true).setPopUpTo(
                    it.findStartDestination().id,
                    inclusive = false,
                    saveState = false
                ).build()

                /* Переходит по диплинку на экран */
                runCatching {
                    navigationController.navigate(tabDeepLink.uri, option)
                }
            }
        }
    }
}