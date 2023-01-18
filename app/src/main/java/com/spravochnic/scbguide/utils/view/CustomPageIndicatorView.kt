package com.spravochnic.scbguide.utils.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2
import com.spravochnic.scbguide.R
import com.spravochnic.scbguide.base.ui.adapter.BaseListAdapter
import com.spravochnic.scbguide.databinding.ViewCustomIndicatorBinding
import com.spravochnic.scbguide.databinding.ViewCustomPageIndicatorBinding

class CustomPageIndicatorView : FrameLayout {

    private val binding =
        ViewCustomPageIndicatorBinding.inflate(LayoutInflater.from(context), this, true)

    private val adapter by lazy { PagerIndicatorAdapter() }

    private var viewPager: ViewPager2? = null
    private val pagesList = mutableListOf<Pade>()
    private var currentItem = 0

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            currentItem = position
            setListToAdapter()
        }
    }

    private fun setListToAdapter() {
        adapter.submitList(pagesListMapping()) {
            viewPager?.setCurrentItem(currentItem, false)
        }
    }

    fun attachToViewPager(
        viewPager2: ViewPager2
    ) {
        pagesList.clear()

        viewPager = viewPager2
        viewPager?.registerOnPageChangeCallback(pageChangeListener)

        initAdapter()

        currentItem = viewPager?.currentItem ?: 0

        viewPager?.adapter?.itemCount?.let { itemCount ->
            repeat(itemCount) {
                pagesList.add(Pade(isActive = it == currentItem))
            }
            adapter.submitList(pagesList)
        }
    }

    private fun initAdapter() = with(binding) {
        root.adapter = adapter
        root.itemAnimator = null
    }

    private fun pagesListMapping(): List<Pade> {
        return pagesList.mapIndexed { index, pade ->
            pade.copy(isActive = index == currentItem)
        }
    }

    data class Pade(val isActive: Boolean = false)

    private class PagerIndicatorAdapter : BaseListAdapter<Pade>() {
        override fun build() {
            baseViewHolder(Pade::class, ViewCustomIndicatorBinding::inflate) { item ->
                binding.root.setImageResource(if (item.isActive) R.drawable.dot_indicator_active else R.drawable.dot_indicator_incative)
            }
        }
    }
}